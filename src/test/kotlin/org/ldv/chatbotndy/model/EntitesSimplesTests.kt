package org.ldv.chatbotndy.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import org.ldv.chatbotndy.model.entity.Message
import org.ldv.chatbotndy.model.entity.TypeMessage
import org.ldv.chatbotndy.model.entity.Conversation

/** Ces tests unitaires n'ont pas besoin d'une base MariaDB. */
class EntitesSimplesTests {
    @Test
    fun `le message est de type utilisateur par defaut`() {
        val message = Message(contenu = "Bonjour")
        assertEquals(TypeMessage.UTILISATEUR, message.typeMessage)
    }

    @Test
    fun `une conversation recoit ses dates`() {
        val conversation = Conversation(titre = "Test")
        assertNotNull(conversation.dateCreation)
        assertNotNull(conversation.dateModification)
    }
}
