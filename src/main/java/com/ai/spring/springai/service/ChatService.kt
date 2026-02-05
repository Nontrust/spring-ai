package com.ai.spring.springai.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.messages.AssistantMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatClient: ChatClient
) {

    /**
     * User message : 사용자가 AI에게 하는 질문이나 요청입니다.
     */
    fun chat(message: String): String =
        chatClient.prompt()
            .user(message) // User message
            .call()
            .content() ?: throw IllegalStateException("AI의 응답이 없습니다.")

    /**
     * System message :AI의 성격, 역할, 제약사항을 정의합니다. 대화 전체에 영향을 미칩니다.
     */
    fun chatWithSystemMessage(message: String) =
        chatClient.prompt()
            .system("""또한, 당신은 언어를 쉽게 설명하는 전문 번역가입니다.
                |모든 입력을 자연스럽고 어렵지 않은 한국어로 번역해주세요""".trimMargin()) // System message
            .user(message)
            .call()
            .content()

    /**
     * Assistant message : AI의 이전응답입입니다.
     * 대화 이력을 유지하는데 사용됩니다.
     *
     */
    fun chatWithResentlyMessage() =
        run { chatClient.prompt()
            .messages( //  Assistant message
                UserMessage("저는 Nontrust입니다."),
                AssistantMessage("안녕하세요 Nontrust님! 반갑습니다."),
                UserMessage("제이름이 뭐라고 했죠?")
            )
            .call()
            .content()
        }

    /**
     * Temperature : Temperature는 AI 응답의 창의성을 조절하는 파라미터
     * 범위: 0 ~ 2.0
     */
    fun chatWithTemperature(creativity: Double) =
        chatClient.prompt()
            .user("외계인이 지구에 오는 SF 소설의 첫 문장을 써주세요")
            .options(OpenAiChatOptions.builder()
                .temperature(creativity) // 높을수록 창의적
                .build())
            .call()
            .content()

    /**
     * MaxTokens : 최대 응답 길이를 제한하는 파라미터
     * 범위: 1 ~ 모델 최대값
     * 용도: 짧은 답변 vs 긴 설명
     */
    fun chatWithMaxTokens(maxTokens: Int) =
        chatClient.prompt()
            .user("한국의 역사를 설명해주세요")
            .options(OpenAiChatOptions.builder()
                .maxTokens(maxTokens) // 응답 길이 제한
                .build())
            .call()
            .content()

    /**
     * TopP : 누적 확률 샘플링 (nucleus sampling)
     * 범위: 0.0 ~ 1.0
     * 용도: 다양성 조절 (낮을수록 확실한 답변, 높을수록 다양한 표현)
     */
    fun chatWithTopP(topP: Double) =
        chatClient.prompt()
            .user("봄을 묘사하는 시를 써주세요")
            .options(OpenAiChatOptions.builder()
                .topP(topP) // 낮을수록 확실한 단어만 선택
                .build())
            .call()
            .content()

    /**
     * FrequencyPenalty : 반복 억제 파라미터
     * 범위: -2.0 ~ 2.0
     * 용도: 같은 단어 반복 방지 (양수: 반복 억제, 음수: 반복 허용)
     */
    fun chatWithFrequencyPenalty(frequencyPenalty: Double) =
        chatClient.prompt()
            .user("사과에 대해 자세히 설명해주세요")
            .options(OpenAiChatOptions.builder()
                .frequencyPenalty(frequencyPenalty) // 양수면 같은 단어 반복 억제
                .build())
            .call()
            .content()

    /**
     * PresencePenalty : 새 토픽 유도 파라미터
     * 범위: -2.0 ~ 2.0
     * 용도: 다양한 주제 탐색 유도 (양수: 새 주제 유도, 음수: 현재 주제 유지)
     */
    fun chatWithPresencePenalty(presencePenalty: Double) =
        chatClient.prompt()
            .user("기술 트렌드에 대해 이야기해주세요")
            .options(OpenAiChatOptions.builder()
                .presencePenalty(presencePenalty) // 양수면 새로운 토픽으로 유도
                .build())
            .call()
            .content()

    /**
     * 모든 옵션을 조합한 예시
     * 창의적인 글쓰기에 최적화된 설정
     */
    fun chatWithAllOptions(
        temperature: Double = 0.8,
        maxTokens: Int = 500,
        topP: Double = 0.9,
        frequencyPenalty: Double = 0.5,
        presencePenalty: Double = 0.5
    ) =
        chatClient.prompt()
            .user("미래 도시의 하루를 묘사하는 짧은 이야기를 써주세요")
            .options(OpenAiChatOptions.builder()
                .temperature(temperature)         // 창의성 조절
                .maxTokens(maxTokens)             // 응답 길이 제한
                .topP(topP)                       // 다양성 조절
                .frequencyPenalty(frequencyPenalty) // 반복 억제
                .presencePenalty(presencePenalty)   // 새 토픽 유도
                .build())
            .call()
            .content()
}