package com.redhat.opendemos.data;


import com.redhat.opendemos.model.Product;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class ProductListProducer {

    @Inject
    private ProductRepository productRepository;

    private List<Product> products;

    @Produces
    @Named
    public List<Product> getProducts(){
        return products;
    }

    public void onProductListChanged(@Observes (notifyObserver = Reception.IF_EXISTS) final Product product){
        retrieveAllProductsOrderedByName();
    }

    @PostConstruct
    public void retrieveAllProductsOrderedByName(){
        products = productRepository.findAllOrderedByName();
    }

}
