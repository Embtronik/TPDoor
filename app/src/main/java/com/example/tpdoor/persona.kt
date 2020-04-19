package com.example.tpdoor

import kotlin.properties.Delegates

class persona(val nombre: String,val email: String,val password: String,val key: String) {
    var _nombre : String by Delegates.notNull()
    var _email : String by Delegates.notNull()
    var _password : String by Delegates.notNull()
    var _key : String by Delegates.notNull()

    init {
        this._nombre = nombre
        this._email=email
        this._password=password
        this._key=key
    }
}
