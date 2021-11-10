package com.allservicerhyno.aplicacion.authenticate;

public class Authentication {

    public Authentication(String jsonrpc, Params params){
        this.jsonrpc = jsonrpc;
        this.params = params;
    }

    public static class Params {

        public Params(String db, String login, String password){
            this.db = db;
            this.login = login;
            this.password = password;
        }

        public String db;
        public String login;
        public String password;
    }

    public String jsonrpc;
    public Params params;
}
