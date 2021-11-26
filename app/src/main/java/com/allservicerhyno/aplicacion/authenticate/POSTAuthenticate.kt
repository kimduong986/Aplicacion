package com.allservicerhyno.aplicacion.authenticate

import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Body

interface POSTAuthenticate {
    @POST("web/session/authenticate")
    fun authenticate(@Body auth: Authentication?): Call<AuthenticationData?>?
}