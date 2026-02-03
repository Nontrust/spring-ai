package com.ai.spring.springai.global.property

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "app.ai.openai.prompt")
class AiPromptProperties {

    @field:Valid
    var v1: V1 = V1()

    class V1 {
        @field:Valid
        var ko: Ko = Ko()
    }

    class Ko {
        @NotBlank
        lateinit var systemDefaultPath: String
    }
}