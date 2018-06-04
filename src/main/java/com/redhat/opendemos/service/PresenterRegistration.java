package com.redhat.opendemos.service;


import com.redhat.opendemos.model.Presenter;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

@Stateless
public class PresenterRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Presenter> userEvent;

    public void registerUser(Presenter presenter) throws Exception {
        log.info("Registering " + presenter.getFirstName() + " " + presenter.getLastName());
        em.persist(presenter);
        userEvent.fire(presenter);
    }
}
