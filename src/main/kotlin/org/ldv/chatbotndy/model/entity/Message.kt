package org.ldv.chatbotndy.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "message")
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null,

    // TEXT permet de stocker de longues réponses, y compris du Markdown.
    @Column(columnDefinition = "TEXT", nullable = false)
    var contenu: String = "",

    @Column(name = "date_creation", nullable = false)
    var dateCreation: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(name = "type_message", nullable = false, length = 20)
    var typeMessage: TypeMessage = TypeMessage.UTILISATEUR,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conversation_id", nullable = false)
    var conversation: Conversation? = null
)
