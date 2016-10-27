package com.epatec.construtecmovil;

import java.util.ArrayList;

public class UserDataHolder {
    private static UserDataHolder instance = null;

    //public String user = "fasm22";
    public String user = "";
    //public String userID = "2014068784";
    public String userID = "";

    public String userROLE = "";
    public String userType = "";
    public String actualProject = "";


    public ArrayList<Producto> shoppingcart;

    private UserDataHolder() {
        shoppingcart = new ArrayList<>();
    }

    public static UserDataHolder getInstance() {
        if(instance == null) {
            instance = new UserDataHolder();
        }
        return instance;
    }

    public void setUser(String pNewUser)
    {
        user =  pNewUser;
    }

    public void setUserROLE(String pNewRole)
    {
        userROLE =  pNewRole;
    }
    
}
