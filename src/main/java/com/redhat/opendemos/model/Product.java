package com.redhat.opendemos.model;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Set;


@Entity
@Cacheable
@XmlRootElement
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"), name = "products")
public class Product implements Serializable {


    public Product (){

    }

    public Product(String name){
        this.name = name;
    }


    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 5, max = 512)
    private String name;

    @Size(max = 4096)
    private String description;

    @URL
    private String url;

    @ManyToMany (mappedBy = "products")
    private Set<Session> sessions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Session> getSessions() { return sessions;}

    public void setSessions(Set<Session> sessions) { this.sessions = sessions;}
}
