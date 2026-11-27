package org.ldv.chatbotndy.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/** Garde la vitrine Sprint 1 ; la racine / affiche maintenant le chatbot (Séance 2). */
@Controller
class ChatBotController {
    @GetMapping("/presentation")
    fun presentation(): String = "utilisateur/accueil"

    @GetMapping("/connexion", "/login")
    fun connexion(): String = "auth/connexion"

    @GetMapping("/exemple")
    fun exemple(): String = "exemple"
}
