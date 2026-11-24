package org.ldv.chatbotndy.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/** Contrôleur des deux écrans demandés au Sprint 1, séance 1. */
@Controller
class ChatBotController {

    @GetMapping("/")
    fun accueil(): String = "utilisateur/accueil"

    @GetMapping("/connexion")
    fun connexion(): String = "auth/connexion"

    @GetMapping("/exemple")
    fun exemple(): String = "exemple"
}
