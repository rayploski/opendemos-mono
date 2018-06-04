package com.redhat.opendemos.model;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@Entity
@XmlRootElement
@Table(name = "reminders")
public class Reminder implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Email
    private String email;

    @NotNull
    @ManyToOne
    private Session session;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
