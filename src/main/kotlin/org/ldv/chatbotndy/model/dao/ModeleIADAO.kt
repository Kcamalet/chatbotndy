package org.ldv.chatbotndy.model.dao

import org.ldv.chatbotndy.model.entity.ModeleIA
import org.springframework.data.jpa.repository.JpaRepository

interface ModeleIADAO : JpaRepository<ModeleIA, Long> {
    fun existsByNom(nom: String): Boolean
}
