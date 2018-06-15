package com.redhat.opendemos.service;

import com.redhat.opendemos.model.Presenter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PresenterService extends AbstractService<Presenter> implements Serializable {

    public PresenterService() { super(Presenter.class); }



    @Override
    protected Predicate[] getSearchPredicates(Root<Presenter> root, Presenter example) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();

        String email = example.getEmail();

        if(email != null && !"".equals(email)){
            predicatesList.add(builder.like(builder.lower(root.<String> get("email")), '%' + email.toLowerCase() + '%'));
        }

        String firstName = example.getFirstName();

        if (firstName != null && !"".equals(firstName)){
            predicatesList.add(builder.like(builder.lower(root.<String> get("firstName")), '%' + firstName.toLowerCase() + '%'));
        }

        String lastName = example.getLastName();

        if (lastName != null && !"".equals(lastName)){
            predicatesList.add(builder.like(builder.lower(root.<String> get("lastName")), '%' + lastName.toLowerCase() + '%'));
        }

        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }
}
