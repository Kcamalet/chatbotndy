package org.ldv.chatbotndy.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "modele_ia")
class ModeleIA(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null,

    @Column(nullable = false, unique = true, length = 160)
    var nom: String = "",

    @Column(name = "identifiant_api", length = 160)
    var identifiantAPI: String = "",

    @Column(name = "url_api", nullable = false, length = 800)
    var urlAPI: String = "",

    @Column(columnDefinition = "TEXT", nullable = false)
    var description: String = "",

    @Column(name = "chemin_logo", length = 500)
    var cheminLogo: String = "",

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fournisseur_id", nullable = false)
    var fourniseurIA: FourniseurIA? = null,

    @OneToMany(mappedBy = "modeleIA")
    var conversations: MutableList<Conversation> = mutableListOf()
)
