package org.ldv.chatbotndy.dto.mistral

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class MistralChatRequest(val model: String, val messages: List<MistralMessage>)
