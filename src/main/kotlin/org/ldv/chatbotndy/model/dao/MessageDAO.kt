package org.ldv.chatbotndy.model.dao

import org.ldv.chatbotndy.model.entity.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageDAO : JpaRepository<Message, Long> {

}
