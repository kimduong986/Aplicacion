package com.allservicerhyno.aplicacion.authenticate;

public class AuthenticationData {

    public static class Result {
        public int uid;
        public String name;
        public String username;
    }

    public String jsonrpc;
    public Object id;
    public Result result;
}
