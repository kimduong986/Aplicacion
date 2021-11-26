package com.allservicerhyno.aplicacion.authenticate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface POSTAuthenticate {

    @POST("web/session/authenticate")
    public Call<AuthenticationData> authenticate (@Body Authentication auth);
}
