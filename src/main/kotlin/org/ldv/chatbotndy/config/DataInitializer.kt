package org.ldv.chatbotndy.config

import org.ldv.chatbotndy.model.dao.FourniseurIADAO
import org.ldv.chatbotndy.model.dao.ModeleIADAO
import org.ldv.chatbotndy.model.entity.FourniseurIA
import org.ldv.chatbotndy.model.entity.ModeleIA
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Sprint 1 - Séance 1 : remplit les fournisseurs et modèles sans doublons.
 * ATTENTION : les noms de modèles Gemini fournis dans le TP sont des exemples ;
 * vérifier leur disponibilité avant de créer les appels réels dans un autre sprint.
 * Cette classe n'appelle aucune API et ne nécessite aucune clé.
 */
@Configuration
class DataInitializer(
    private val fourniseurIADAO: FourniseurIADAO,
    private val modeleIADAO: ModeleIADAO
) {
    @Bean
    fun initData(): CommandLineRunner = CommandLineRunner {
        val mistral = fourniseurIADAO.findAll()
            .firstOrNull { it.nom == "Mistral" }
            ?: fourniseurIADAO.save(
                FourniseurIA(
                    nom = "Mistral",
                    cheminLogo = "/images/mistral.png",
                    description = "Fournisseur français de modèles d'intelligence artificielle."
                )
            )

        val gemini = fourniseurIADAO.findAll()
            .firstOrNull { it.nom == "Gemini" }
            ?: fourniseurIADAO.save(
                FourniseurIA(
                    nom = "Gemini",
                    cheminLogo = "/images/gemini.png",
                    description = "Modèles d'intelligence artificielle de Google."
                )
            )

        // Noms reproduits depuis le support TP, à valider avec la documentation API.
        val modeles = listOf(
            ModeleIA(
                nom = "gemini-3.8-flash",
                identifiantAPI = "gemini-3.8-flash",
                urlAPI = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.8-flash:generateContent",
                description = "Modèle rapide et polyvalent (identifiant du TP à vérifier).",
                cheminLogo = "/images/gemini.png",
                fourniseurIA = gemini
            ),
            ModeleIA(
                nom = "gemini-3.5-flash-lite",
                identifiantAPI = "gemini-3.5-flash-lite",
                urlAPI = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash-lite:generateContent",
                description = "Modèle pour les tâches simples (identifiant du TP à vérifier).",
                cheminLogo = "/images/gemini.png",
                fourniseurIA = gemini
            ),
            ModeleIA(
                nom = "mistral-small-latest",
                identifiantAPI = "mistral-small-latest",
                urlAPI = "https://api.mistral.ai/v1/chat/completions",
                description = "Modèle léger pour les conversations et tâches courantes.",
                cheminLogo = "/images/mistral.png",
                fourniseurIA = mistral
            ),
            ModeleIA(
                nom = "mistral-medium-latest",
                identifiantAPI = "mistral-medium-latest",
                urlAPI = "https://api.mistral.ai/v1/chat/completions",
                description = "Modèle avancé pour les tâches complexes.",
                cheminLogo = "/images/mistral.png",
                fourniseurIA = mistral
            )
        )

        for (modele in modeles) {
            if (!modeleIADAO.existsByNom(modele.nom)) {
                modeleIADAO.save(modele)
            }
        }
    }
}
