package com.allservicerhyno.aplicacion.authenticate

class AuthenticationData {
    class Result {
        var uid = 0
        var name: String? = null
        var username: String? = null
    }

    var jsonrpc: String? = null
    var id: Any? = null
    var result: Result? = null
}