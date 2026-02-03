package com.ai.spring.springai.global.config

import com.ai.spring.springai.global.component.PromptLoader
import com.ai.spring.springai.global.property.AiPromptProperties
import org.springframework.ai.chat.client.ChatClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatClientConfig(
    private val promptLoader: PromptLoader,
    private val props: AiPromptProperties
) {
    /**
     * Creates and configures a `ChatClient` bean by loading the necessary system prompt
     * and setting it as the default system configuration in the provided `ChatClient.Builder`.
     *
     * @param chatClientBuilder The `ChatClient.Builder` used to build and configure the `ChatClient`.
     * @return The configured `ChatClient` instance.
     */
    @Bean
    fun chatClient(chatClientBuilder: ChatClient.Builder): ChatClient =
        promptLoader.load(props.v1.ko.systemDefault)
            .let(promptLoader::load)
            .let(chatClientBuilder::defaultSystem)
            .build()
}