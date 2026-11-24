package org.ldv.chatbotndy.model.dao

import org.ldv.chatbotndy.model.entity.Conversation
import org.springframework.data.jpa.repository.JpaRepository

interface ConversationDAO : JpaRepository<Conversation, Long> {

}
