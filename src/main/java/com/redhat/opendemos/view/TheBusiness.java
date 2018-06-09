package com.redhat.opendemos.view;


import com.redhat.opendemos.model.Presenter;
import com.redhat.opendemos.model.Product;
import com.redhat.opendemos.model.Session;
import com.redhat.opendemos.util.Loggable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.swing.plaf.metal.MetalBorders;
import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;


@Named
@Loggable
@CatchException
@ApplicationScoped
public class TheBusiness implements Serializable {


    private String message = "Hello OpenDemos";

    private List<Session> sessions = new ArrayList<Session>();

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public TheBusiness() {
        buildOutDefaultSessions();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private void buildOutDefaultSessions(){
        Session s = new Session();
        s.setTitle("The future of Red Hat Virtualization");
        s.setDescription("A demonstration of RHEV 4.x");
        Presenter tbd = new Presenter();
        tbd.setFirstName("TBD");
        s.getPresenters().add(tbd);
        Product rhev = new Product();
        rhev.setName("RHEV");
        s.getProducts().add(rhev);
        s.setSessionDate(LocalDate.of(2018,06,05));
        sessions.add(s);

        Session s2 = new Session();
        s2.setTitle("Geting to Know CloudForms");
        s2.setDescription("Learn more about how wonderful CloudForms is in this demonstration.");
        s2.getPresenters().add(tbd);
        Product cloudForms = new Product();
        cloudForms.setName("CloudForms");
        s2.getProducts().add(cloudForms);
        s2.setSessionDate(LocalDate.of(2018,06,07));
        sessions.add(s2);


        s = new Session();
        s.setTitle("Red Hat Satellite Fundamentals");
        s.setDescription ("Something witty about Satellite");
        s.getPresenters().add(tbd);
        Product sat = new Product();
        sat.setName("Satellite 6.x");
        s.getProducts().add(sat);
        s.setSessionDate(LocalDate.of(2018, 06, 12));
        sessions.add(s);


        s = new Session();
        s.setTitle("Get Started with Cloud Native App Development");
        s.setDescription("An example of using Java EE, Node.js, vert.x and SpringBoot all upon OpenShift");
        Presenter derrick = new Presenter();
        derrick.setFirstName ("Derrick");
        derrick.setLastName("Kittler");
        Presenter eric = new Presenter();
        eric.setFirstName("Eric");
        eric.setLastName("Getchell");
        s.getPresenters().add(derrick);
        s.getPresenters().add(eric);
        Product rhoar = new Product ("RHOAR");
        Product eap = new Product ("JBoss Enterprise Application Platform");
        Product ocp = new Product("OpenShift Container Platform");
        s.getProducts().add(rhoar);
        s.getProducts().add(eap);
        s.getProducts().add(ocp);
        s.setSessionDate(LocalDate.of(2018, 06, 14));
        s.setSessionTime(LocalTime.of(15,00));

        sessions.add(s);

    }
}