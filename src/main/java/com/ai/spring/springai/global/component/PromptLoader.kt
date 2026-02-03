package com.ai.spring.springai.global.component

import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component

@Component
class PromptLoader(private val resourceLoader: ResourceLoader) {
    fun load(path: String): String =
        resourceLoader.getResource(path)
            .inputStream.bufferedReader()
            .use { it.readText() }

}