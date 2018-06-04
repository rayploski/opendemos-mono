package com.redhat.opendemos.service;

import com.redhat.opendemos.model.Session;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

@Stateless
public class SessionRegistration {
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Session> sessionEvent;

    public void registerSession(Session session) throws Exception {
        log.info("Registering " + session.getTitle());
        em.persist(session);
        sessionEvent.fire(session);
    }


}
