package com.example.rickywiki.model;

import com.orm.SugarRecord;

public class Usuario extends SugarRecord<Usuario> {
    private String user;
    private String passw;

    public Usuario(String user, String passw){
        this.user = user;
        this.passw = passw;
    }

    public Usuario(){
    }

    public String getUser() {
        return user;
    }

    public String getPassw() {
        return passw;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }
}
