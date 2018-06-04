package com.redhat.opendemos.util;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;


public class Resources {


    @Produces
    @PersistenceContext(name = "primary", unitName = "primary")
    private EntityManager em;

    @Produces
    public Logger produceLog(InjectionPoint injectionPoint){
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

}
