package org.ldv.chatbotndy.model.dao

import org.ldv.chatbotndy.model.entity.Conversation
import org.springframework.data.jpa.repository.JpaRepository

interface ConversationDAO : JpaRepository<Conversation, Long> {
    // Entité Séance 1 : la propriété s'appelle dateModification,
    // PAS dateDerniereModification comme dans l'exemple du prof.
    fun findAllByOrderByDateModificationDesc(): List<Conversation>
}
