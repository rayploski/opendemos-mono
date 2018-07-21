package com.redhat.opendemos.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.*;
import java.time.format.*;
import java.util.*;

@Entity
@XmlRootElement
@Table( name = "sessions")
public class Session implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 10, max = 512)
    private String title;

    @Column(name = "session_desc")
    @Size(max = 4096)
    private String description;

    @Column (name = "session_date")
    private LocalDate sessionDate;

    @Column (name = "session_time")
    private LocalTime sessionTime;


    @Column (name="session_url")
    private String url;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable (name="session_product")
    private Set<Product> products = new HashSet<Product>();

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable (name="session_presenters")
    private Set<Presenter> presenters = new HashSet<Presenter>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Set<Product> getProducts(){return this.products;}

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
    }

    public LocalTime getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(LocalTime sessionTime) {
        this.sessionTime = sessionTime;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Presenter> getPresenters() {
        return presenters;
    }

    public void setPresenters(Set<Presenter> presenters) {
        this.presenters = presenters;
    }

    public List<Product> getProductList(){
    	return new ArrayList<Product>(this.products);
    }
    
    public List<Presenter>getPresenterList(){
    	return new ArrayList<Presenter>(this.presenters);
    }

    public String getSessionDateStr(){
        return sessionDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }

    public String getSessionTimeStr(){
        if (sessionTime != null & sessionTime != LocalTime.MIDNIGHT) {
            return DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(sessionTime) + " EDT";
        }
        return "TBD";
    }
}
