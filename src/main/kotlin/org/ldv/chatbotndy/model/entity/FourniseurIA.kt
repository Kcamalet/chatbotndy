package org.ldv.chatbotndy.model.entity

import jakarta.persistence.*

/** Conserve l'orthographe « FourniseurIA » utilisée dans le TP. */
@Entity
@Table(name = "fournisseur_ia")
class FourniseurIA(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null,

    @Column(nullable = false, unique = true, length = 120)
    var nom: String = "",

    @Column(columnDefinition = "TEXT", nullable = false)
    var description: String = "",

    @Column(name = "chemin_logo", length = 500)
    var cheminLogo: String = "",

    @OneToMany(mappedBy = "fourniseurIA")
    var modeles: MutableList<ModeleIA> = mutableListOf()
)
