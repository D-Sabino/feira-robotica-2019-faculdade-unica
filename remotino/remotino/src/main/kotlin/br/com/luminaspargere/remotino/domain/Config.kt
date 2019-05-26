package br.com.luminaspargere.remotino.domain

import com.chibatching.kotpref.KotprefModel

object Config : KotprefModel() {
    var address: String by stringPref()
}