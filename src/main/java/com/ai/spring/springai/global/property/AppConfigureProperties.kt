package com.ai.spring.springai.global.property

import jakarta.validation.Valid
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "app.configure")
data class AppConfigureProperties(
    @field:Valid
    val conversation: Conversation
) {
    data class Conversation(
        val maxHistory: Int
    )
}