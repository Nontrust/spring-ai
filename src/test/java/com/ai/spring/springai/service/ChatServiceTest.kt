package com.ai.spring.springai.service

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor


@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ChatServiceTest(private val chatService: ChatService) {
    @Test
    fun chatWithSystemMessage() {
        val message = chatService.chatWithSystemMessage("wassup man")
        println("message : $message")
    }

    @Test
    fun chatWithResentlyMessage() {
        val message = chatService.chatWithResentlyMessage()
        println("message : $message")
    }

}