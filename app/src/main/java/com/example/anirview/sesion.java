package com.example.anirview;

public class sesion {

    private boolean open;
    private String user;

    public void sesion(boolean open, String user){
        this.open=open;
        this.user=user;
    }

    public boolean getOpen(){
        return open;
    }

    public String getUser(){
        return user;
    }

    public void setUser(String user){
        this.user=user;
    }

    public void open(){
        open=true;
    }
    public void close(){
        open=false;
    }
}
