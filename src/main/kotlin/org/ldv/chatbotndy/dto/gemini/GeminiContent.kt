package org.ldv.chatbotndy.dto.gemini

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class GeminiContent(val role: String = "", val parts: List<GeminiPart> = emptyList())
