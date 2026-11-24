package org.ldv.chatbotndy.service

import org.ldv.chatbotndy.dto.gemini.*
import org.ldv.chatbotndy.model.entity.ModeleIA
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.net.URI

@Service
class GeminiService(
    private val restClientBuilder: RestClient.Builder,
    @Value("\${gemini.api.key:}") private val apiKey: String
) : IAService {
    override fun getFournisseur() = "Gemini"

    override fun envoyerPrompt(prompt: String, modele: ModeleIA): String {
        require(apiKey.isNotBlank() && !apiKey.startsWith("VOTRE", ignoreCase = true)) {
            "Clé Gemini non configurée : créez une clé gratuite sur Google AI Studio."
        }
        val url = URI(modele.urlAPI)
        require(url.scheme == "https" && url.host == "generativelanguage.googleapis.com"
                && url.path.startsWith("/v1beta/models/") && url.path.endsWith(":generateContent")) {
            "Endpoint Gemini non autorisé."
        }
        val request = GeminiChatRequest(
            contents = listOf(GeminiContent(role = "user", parts = listOf(GeminiPart(text = prompt))))
        )
        val response = restClientBuilder.build().post()
            .uri(url)
            .header("x-goog-api-key", apiKey)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(GeminiChatResponse::class.java)
        return response?.candidates?.firstOrNull()?.content?.parts
            ?.joinToString("\n") { it.text }?.takeIf { it.isNotBlank() }
            ?: "Aucune réponse reçue de Gemini."
    }
}
