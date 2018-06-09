package com.redhat.opendemos.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@SuppressWarnings("serial")
@Entity
@XmlRootElement
@Table( name = "presenters")
public class Presenter implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 5, max = 512)
    private String firstName;

    @NotNull
    @Size(min = 5, max = 512)
    private String lastName;

    @Size(min = 3, max = 512)
    @Email
    private String email;

    @Size(max = 512)
    private String biography;

    @Size(max = 128)
    private String twitterHandle;

    @Size(max = 512, message = "The length of the image URL is limited to 512 characters")
    private String imageUrl;

    @Size(max = 512, message = "Come On! Who is this person?  Title really has to be less than 512 characters.")
    private String title;

    @Size(max = 256, message = "The size of the LinkedIn Profile URL should be under 256 characters or should be omitted.")
    @URL
    private String linkedIn;

    @Size(max = 64)
    private String redhatId;

    @Size(max = 64)
    private String githubId;

    @Size(max = 64)
    private String stackOverflowId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }


    public String getRedhatId() {
        return redhatId;
    }

    public void setRedhatId(String redhatId) {
        this.redhatId = redhatId;
    }

    public String getGithubId() {
        return githubId;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    public String getStackOverflowId() {
        return stackOverflowId;
    }

    public void setStackOverflowId(String stackOverflowId) {
        this.stackOverflowId = stackOverflowId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String eml){
        this.email = eml;
    }

}
