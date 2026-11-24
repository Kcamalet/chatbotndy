package org.ldv.chatbotndy.model.dao

import org.ldv.chatbotndy.model.entity.FourniseurIA
import org.springframework.data.jpa.repository.JpaRepository

interface FourniseurIADAO : JpaRepository<FourniseurIA, Long> {
    fun existsByNom(nom: String): Boolean
}
