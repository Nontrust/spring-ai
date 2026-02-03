package com.ai.spring.springai.global.property

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.ai.openai.prompt")
class AiPromptProperties {
    var v1: V1 = V1()

    class V1 {
        var ko: Ko = Ko()
    }

    class Ko {
        @NotBlank
        lateinit var systemDefault: String
    }
}
