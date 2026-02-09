package com.ai.spring.springai.global.property

import com.ai.spring.springai.global.enum.PromptStyle
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "app.ai.openai.prompt")
class AiPromptProperties {

    @field:Valid
    lateinit var systemDefault: SystemDefaultGroup

    @field:Valid
    lateinit var style: StyleGroup

    class SystemDefaultGroup {
        @NotBlank
        lateinit var dir: String

        @field:Valid
        lateinit var default: Template
    }

    class StyleGroup {
        @NotBlank
        lateinit var dir: String

        @field:Valid
        lateinit var templates: Map<PromptStyle, Template>
    }

    class Template {
        @NotBlank
        lateinit var fileName: String
    }
}