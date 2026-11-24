package org.ldv.chatbotndy.service

import org.ldv.chatbotndy.model.entity.ModeleIA

interface IAService {
    fun getFournisseur(): String
    fun envoyerPrompt(prompt: String, modele: ModeleIA): String
}
