package com.allservicerhyno.aplicacion.authenticate

class Authentication(var jsonrpc: String, var params: Params) {
    class Params(var db: String, var login: String, var password: String)
}