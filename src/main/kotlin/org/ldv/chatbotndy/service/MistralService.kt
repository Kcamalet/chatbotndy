package org.ldv.chatbotndy.service

import org.ldv.chatbotndy.dto.mistral.*
import org.ldv.chatbotndy.model.entity.ModeleIA
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class MistralService(
    private val restClientBuilder: RestClient.Builder,
    @Value("\${mistral.api.key:}") private val apiKey: String
) : IAService {
    override fun getFournisseur() = "Mistral"

    override fun envoyerPrompt(prompt: String, modele: ModeleIA): String {
        require(apiKey.isNotBlank() && !apiKey.startsWith("VOTRE", ignoreCase = true)) {
            "Clé Mistral non configurée : demandez une clé à l'enseignant ou utilisez Gemini."
        }
        // La séance 1 utilise des endpoints prédéfinis ; refuse une URL non Mistral.
        require(modele.urlAPI == "https://api.mistral.ai/v1/chat/completions") { "Endpoint Mistral non autorisé." }
        val request = MistralChatRequest(
            model = modele.identifiantAPI.ifBlank { modele.nom },
            messages = listOf(MistralMessage(role = "user", content = prompt))
        )
        val response = restClientBuilder.build().post()
            .uri(modele.urlAPI)
            .header("Authorization", "Bearer $apiKey")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(MistralChatResponse::class.java)
        return response?.choices?.firstOrNull()?.message?.content
            ?.takeIf { it.isNotBlank() } ?: "Aucune réponse reçue de Mistral."
    }
}
