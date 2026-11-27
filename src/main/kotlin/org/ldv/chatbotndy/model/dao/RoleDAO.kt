package org.ldv.chatbotndy.model.dao

import org.ldv.chatbotndy.model.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleDAO : JpaRepository<Role, Long> {
}