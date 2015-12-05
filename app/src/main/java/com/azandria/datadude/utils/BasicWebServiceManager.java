package com.azandria.datadude.utils;

import com.raizlabs.webservicemanager.webservicemanager.WebServiceManager;

public class BasicWebServiceManager extends WebServiceManager {

    public static BasicWebServiceManager INSTANCE = new BasicWebServiceManager();
    public static BasicWebServiceManager get() { return INSTANCE; }

}
