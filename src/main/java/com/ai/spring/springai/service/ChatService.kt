package com.ai.spring.springai.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service

@Service
class ChatService(chatClientBuilder: ChatClient.Builder) {
    private val chatClient = chatClientBuilder
        .defaultSystem("""
            당신은 친절한 AI 비서 입니다.
            한국어로 답변 해 주세요
        """.trimIndent())
        .build()

    fun chat(message: String): String{
        return chatClient.prompt()
            .user(message)
            .call()
            .content()?:throw IllegalStateException("AI의 응답이 없습니다.");
    }

}