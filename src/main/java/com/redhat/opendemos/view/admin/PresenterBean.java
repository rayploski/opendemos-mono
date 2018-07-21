package com.redhat.opendemos.view.admin;

import com.redhat.opendemos.model.Presenter;
import com.redhat.opendemos.util.Loggable;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.faces.convert.Converter;


@Named
@Stateful
@ConversationScoped
@Loggable
public class PresenterBean {

    private static final long serialVersionUID =1L;

    private Long id;
    private Presenter presenter;

    @Inject
    private Conversation conversation;

    @PersistenceContext(name="primary", type = PersistenceContextType.EXTENDED)
    private EntityManager em;


    @Resource
    SessionContext sessionContext;


    private int pageSize;
    private long count;
    private List<Presenter> pageItems;
    private Presenter example = new Presenter();


    // ------------------------------------------------------------------------
    // CRUD Methods
    // ------------------------------------------------------------------------

    public String create(){
        this.conversation.begin();
        this.conversation.setTimeout(180000L);
        return "create?faces-redirect=true";
    }

    public void retrieve()
    {

        if (FacesContext.getCurrentInstance().isPostback())
        {
            return;
        }

        if (this.conversation.isTransient())
        {
            this.conversation.begin();
            this.conversation.setTimeout(1800000L);
        }

        if (this.id == null)
        {
            this.presenter = this.example;
        }
        else
        {
            this.presenter = findById(getId());
        }
    }

    public String update()
    {
        this.conversation.end();
        try {
            if (this.id == null)
            {
                this.em.persist(this.presenter);
                return "search?faces-redirect=true";
            } else {
                this.em.merge(this.presenter);
                return "view?faces-redirect=true&id=" + this.presenter.getId();
            }
        } catch (Exception e ) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    public String delete() {
        this.conversation.end();

        try {
            Presenter deletableEntity = findById(getId());

            this.em.remove(deletableEntity);
            this.em.flush();
            return "search?faces-redirect=true";
        }
        catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return  null;
        }
    }

    // ------------------------------------------------------------------------
    // Getters and Setters
    // ------------------------------------------------------------------------

    public Presenter findById(Long id) { return this.em.find(Presenter.class, id); }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }


    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public Presenter getExample() {
        return example;
    }

    public void setExample(Presenter example) {
        this.example = example;
    }

// ------------------------------------------------------------------------

    // Pagination Support
    // ------------------------------------------------------------------------

    public int getPageSize() { return pageSize; }

    public void setPageSize(int page) { this.pageSize = page; }

    public int getPage() { return pageSize; }

    public void setPage(int page) { this.pageSize = page; }


    public long getCount() { return count; }

    public void setCount(long count) { this.count = count; }

    public List<Presenter>getPageItems(){ return pageItems; }

    public void setPageItems(List<Presenter> pageItems) { this.pageItems = pageItems; }



    public void paginate () {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<Presenter> root = countCriteria.from(Presenter.class);
        countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
        this.count = this.em.createQuery(countCriteria).getSingleResult();
        CriteriaQuery<Presenter> criteria = builder.createQuery(Presenter.class);
        root = criteria.from(Presenter.class);
        TypedQuery<Presenter> query = this.em.createQuery(criteria.select(root).where(getSearchPredicates(root)));
        this.pageItems = query.getResultList();
    }


    public List<Presenter> getAll() {
        CriteriaQuery<Presenter> criteriaQuery = this.em
                .getCriteriaBuilder().createQuery(Presenter.class);
        return this.em.createQuery(
                criteriaQuery.select(criteriaQuery.from(Presenter.class))).getResultList();
    }

    private Predicate[] getSearchPredicates(Root<Presenter> root) {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        String email = this.example.getEmail();
        //TODO:  Add more predicates
        if (email != null && !"".equals(email))
        {
            predicatesList.add(builder.like(builder.lower(root.<String> get("email")), '%' + email.toLowerCase() + '%'));
        }


        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }



    public Converter getConverter(){

        final PresenterBean ejbProxy = this.sessionContext.getBusinessObject(PresenterBean.class);
        return new Converter()
        {


            @Override
            public Object getAsObject(FacesContext context, UIComponent component, String value)
            {
                return ejbProxy.findById(Long.valueOf(value));
            }

            @Override
            public String getAsString(FacesContext context, UIComponent component, Object value)
            {
                if (value == null)
                    return "" +
                            "";
                return String.valueOf(((Presenter) value).getId());
            }
        };
    }

    private Presenter add = new Presenter();

    public  Presenter getAdd(){
         return this.add;
    }

    public Presenter getAdded()
    {
        Presenter added = this.add;
        this.add = new Presenter();
        return added;
    }

}
