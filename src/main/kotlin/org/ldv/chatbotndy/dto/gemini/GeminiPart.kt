package org.ldv.chatbotndy.dto.gemini

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class GeminiPart(val text: String = "")
