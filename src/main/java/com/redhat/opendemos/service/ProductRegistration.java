package com.redhat.opendemos.service;


import com.redhat.opendemos.model.Product;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

@Stateless
public class ProductRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Product> productEvent;

     public void registerProduct(Product product){
         log.info("Registering " + product.getName());
         em.persist(product);
         productEvent.fire(product);
     }

}
