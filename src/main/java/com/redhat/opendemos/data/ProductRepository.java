package com.redhat.opendemos.data;


import com.redhat.opendemos.model.Product;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class ProductRepository {

    @Inject
    private EntityManager entityManager;

    public Product findById(Long id) {
        return entityManager.find(Product.class, id);
    }


    public List<Product> findAllOrderedByName(){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
        Root<Product> product = criteria.from(Product.class);
        criteria.select(product).orderBy(cb.asc(product.get("name")));
        return entityManager.createQuery(criteria).getResultList();

    }
}
