package com.redhat.opendemos.data;

import com.redhat.opendemos.model.Presenter;
import com.redhat.opendemos.model.Product;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class PresenterListProducer {


    @Inject
    private PresenterRepository presenterRepository;

    private List<Presenter> presenters;

    @Produces
    @Named
    public List<Presenter> getPresenters(){
        return presenters;
    }

    public void onPresenterListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Presenter presenter){
        retreiveAllPresentersOrderedByName();
    }

    @PostConstruct
    public void retreiveAllPresentersOrderedByName(){
        presenters = presenterRepository.findAllOrderedByName();
    }


}
