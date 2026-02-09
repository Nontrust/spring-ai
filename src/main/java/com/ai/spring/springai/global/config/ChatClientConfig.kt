package com.ai.spring.springai.global.config

import com.ai.spring.springai.global.component.PromptLoader
import com.ai.spring.springai.global.enum.PromptStyle
import com.ai.spring.springai.global.property.AiPromptProperties
import org.springframework.ai.chat.client.ChatClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatClientConfig(
    private val promptLoader: PromptLoader,
    private val props: AiPromptProperties
) {
    private val systemDefaultPrompt by lazy {
        promptLoader.load(props.systemDefault.dir + props.systemDefault.default.fileName)
    }

    private fun stylePrompt(style: PromptStyle): String {
        val tpl = props.style.templates[style]
            ?: error("style.templates.${style.name.lowercase()} is missing")
        return promptLoader.load(props.style.dir + tpl.fileName)
    }

    private fun build(builder: ChatClient.Builder, style: PromptStyle): ChatClient =
        builder.defaultSystem(systemDefaultPrompt + "\n" + stylePrompt(style)).build()

    @Bean("chatClientDefault")
    fun chatClientDefault(builder: ChatClient.Builder): ChatClient =
        build(builder, PromptStyle.DEFAULT)

    @Bean("chatClientConversation")
    fun chatClientConversation(builder: ChatClient.Builder): ChatClient =
        build(builder, PromptStyle.CONVERSATION)
}