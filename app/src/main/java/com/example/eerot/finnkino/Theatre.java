package com.example.eerot.finnkino;

public class Theatre {

    String name = "";
    int ID;

    public void setInfo(String nimi, String id){
        name = nimi;
        ID = Integer.valueOf(id);
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }




}
