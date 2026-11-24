package org.ldv.chatbotndy.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

/** Le modèle sélectionné est fixé lors de la création de la conversation. */
@Entity
@Table(name = "conversation")
class Conversation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null,

    @Column(nullable = false, length = 255)
    var titre: String = "",

    @Column(name = "date_creation", nullable = false)
    var dateCreation: LocalDateTime = LocalDateTime.now(),

    @Column(name = "date_modification", nullable = false)
    var dateModification: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "modele_ia_id", nullable = false, updatable = false)
    var modeleIA: ModeleIA? = null,

    @OneToMany(mappedBy = "conversation", cascade = [CascadeType.ALL], orphanRemoval = true)
    var messages: MutableList<Message> = mutableListOf()
)
