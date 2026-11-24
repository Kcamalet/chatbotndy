package org.ldv.chatbotndy.controller

import org.ldv.chatbotndy.model.dao.ConversationDAO
import org.ldv.chatbotndy.model.dao.MessageDAO
import org.ldv.chatbotndy.model.dao.ModeleIADAO
import org.ldv.chatbotndy.model.entity.Conversation
import org.ldv.chatbotndy.model.entity.Message
import org.ldv.chatbotndy.model.entity.TypeMessage
import org.ldv.chatbotndy.service.IAServiceFactory
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.time.LocalDateTime

@Controller
class ChatController(
    private val iaServiceFactory: IAServiceFactory,
    private val conversationDAO: ConversationDAO,
    private val messageDAO: MessageDAO,
    private val modeleIADAO: ModeleIADAO
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/")
    fun accueil(model: Model): String {
        model.addAttribute("conversation", null)
        model.addAttribute("modeles", modeleIADAO.findAll())
        model.addAttribute("messages", emptyList<Message>())
        model.addAttribute("conversations", conversationDAO.findAllByOrderByDateModificationDesc())
        return "utilisateur/chat"
    }

    @GetMapping("/conversations/{id}")
    fun afficherConversation(@PathVariable id: Long, model: Model): String {
        val conversation = conversationDAO.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Conversation introuvable") }
        model.addAttribute("conversation", conversation)
        model.addAttribute("modeles", emptyList<Any>())
        model.addAttribute("messages", conversation.messages.sortedBy { it.dateCreation })
        model.addAttribute("conversations", conversationDAO.findAllByOrderByDateModificationDesc())
        return "utilisateur/chat"
    }

    @PostMapping("/conversations")
    fun creerConversation(
        @RequestParam prompt: String,
        @RequestParam idModele: Long,
        redirect: RedirectAttributes
    ): String {
        verifierPrompt(prompt)
        val modele = modeleIADAO.findById(idModele)
            .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Modèle IA introuvable") }
        val now = LocalDateTime.now()
        val conversation = conversationDAO.save(
            Conversation(
                titre = prompt.trim().take(50),
                dateCreation = now,
                dateModification = now,
                modeleIA = modele
            )
        )
        sauvegarderMessage(conversation, prompt.trim(), TypeMessage.UTILISATEUR)
        repondre(conversation, prompt.trim(), redirect)
        return "redirect:/conversations/${conversation.id}"
    }

    @PostMapping("/conversations/{id}/messages")
    fun envoyerMessage(
        @PathVariable id: Long,
        @RequestParam prompt: String,
        redirect: RedirectAttributes
    ): String {
        verifierPrompt(prompt)
        val conversation = conversationDAO.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Conversation introuvable") }
        sauvegarderMessage(conversation, prompt.trim(), TypeMessage.UTILISATEUR)
        repondre(conversation, prompt.trim(), redirect)
        return "redirect:/conversations/$id"
    }

    private fun sauvegarderMessage(conversation: Conversation, contenu: String, type: TypeMessage) {
        messageDAO.save(Message(contenu = contenu, dateCreation = LocalDateTime.now(),
            typeMessage = type, conversation = conversation))
    }

    private fun repondre(conversation: Conversation, prompt: String, redirect: RedirectAttributes) {
        try {
            val modele = conversation.modeleIA ?: error("La conversation n'a pas de modèle associé")
            val service = iaServiceFactory.getService(modele)
            val reponse = service.envoyerPrompt(prompt, modele)
            sauvegarderMessage(conversation, reponse, TypeMessage.ASSISTANT)
        } catch (e: Exception) {
            // La question reste enregistrée même sans clé/API ; aucun secret dans la page.
            logger.warn("Impossible d'obtenir une réponse IA : {}", e.javaClass.simpleName)
            val raison = if (e is IllegalArgumentException || e is IllegalStateException) {
                e.message ?: "Fournisseur indisponible"
            } else {
                "Le fournisseur IA ne répond pas (clé, quota, modèle ou réseau)."
            }
            redirect.addFlashAttribute("erreurIA", raison)
        }
        conversation.dateModification = LocalDateTime.now()
        conversationDAO.save(conversation)
    }

    private fun verifierPrompt(prompt: String) {
        if (prompt.isBlank() || prompt.length > 10000) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Le message doit contenir entre 1 et 10 000 caractères")
        }
    }
}
