package com.ai.spring.springai.service

import com.ai.spring.springai.global.property.AppConfigureProperties
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.messages.AssistantMessage
import org.springframework.ai.chat.messages.Message
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class ConversationService(
    @param:Qualifier("chatClientConversation") private val chatClient: ChatClient,
    props: AppConfigureProperties
) {
    // 세션 대화 이력 메모리 저장소
    private val conversations = ConcurrentHashMap<String, MutableList<Message>>()
    private val MAX_HISTORY = props.conversation.maxHistory

    /**
     *
     */
    fun chat(sessionId: String, userMessage: String): String {
        // 1. 대화이력 가저오기
        val history = conversations.computeIfAbsent(sessionId) { mutableListOf() }

        // 2. 메시지 history 길이까지 제거
        while (history.size >= MAX_HISTORY) {
            history.removeAt(0)
        }
        // 3. 현재 메시지 추가
        history.add(UserMessage(userMessage))

        // 4. AI 호출 (전체 메시지)
        val response = chatClient.prompt()
            .messages(history)
            .call()
            .content() ?: throw IllegalStateException("AI의 응답이 없습니다.")

        // 5. 응답 이력 추가
        history.add(AssistantMessage(response))

        return response
    }

    fun  clearHistory(sessionId: String) = conversations.remove(sessionId)
    fun getHistory(sessionId: String) = conversations[sessionId] ?: emptyList()
}