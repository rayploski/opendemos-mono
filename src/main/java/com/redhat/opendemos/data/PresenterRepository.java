package com.redhat.opendemos.data;


import com.redhat.opendemos.model.Presenter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class PresenterRepository {

    @Inject
    private EntityManager entityManager;

    public Presenter findById(Long id){
        return entityManager.find(Presenter.class, id);
    }

    public Presenter findByEmail(String email){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Presenter> criteria = criteriaBuilder.createQuery(Presenter.class);
        Root<Presenter> user = criteria.from(Presenter.class);
        criteria.select(user).where(criteriaBuilder.equal(user.get("email"), email));
        return entityManager.createQuery(criteria).getSingleResult();
    }

    public List<Presenter> findAllOrderedByName() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Presenter> criteriaQuery = cb.createQuery(Presenter.class);
        Root<Presenter> presenter = criteriaQuery.from(Presenter.class);
        criteriaQuery.select(presenter).orderBy(cb.asc(presenter.get("lastName")));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
