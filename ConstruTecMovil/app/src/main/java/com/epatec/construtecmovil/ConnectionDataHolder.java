package com.epatec.construtecmovil;

public class ConnectionDataHolder {

    private static ConnectionDataHolder instance = null;

    public String ipConnection = "192.168.43.115";
    public String portConnection = "7676";
    public boolean online = false;


    public static ConnectionDataHolder getInstance() {
        if(instance == null) {
            instance = new ConnectionDataHolder();
        }
        return instance;
    }

    public void modifyIP(String pNewIp){

        ipConnection = pNewIp;
    }

    public void modifyPort(String pNewPort){

        portConnection = pNewPort;
    }
}
