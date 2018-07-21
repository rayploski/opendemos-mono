package com.redhat.opendemos.service;

import com.redhat.opendemos.model.*;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.redhat.opendemos.util.Loggable;

@Stateless
@LocalBean
@Loggable
public class
ProductService extends AbstractService<Product> implements Serializable
{

    @Inject
    private Event<Product> productEvent;
 

   // ======================================
   // =            Constructors            =
   // ======================================

   public ProductService()
   {
      super(Product.class);
   }




   // ======================================
   // =         Protected methods          =
   // ======================================

   @Override
   protected Predicate[] getSearchPredicates(Root<Product> root, Product example)
   {
      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      String name = example.getName();
      if (name != null && !"".equals(name))
      {
         predicatesList.add(builder.like(builder.lower(root.<String> get("name")), '%' + name.toLowerCase() + '%'));
      }
      String description = example.getDescription();
      if (description != null && !"".equals(description))
      {
         predicatesList.add(builder.like(builder.lower(root.<String> get("description")), '%' + description.toLowerCase() + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }
}
