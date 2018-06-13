package com.redhat.opendemos.view.admin;

import com.redhat.opendemos.model.Presenter;
import com.redhat.opendemos.model.Session;
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

import org.primefaces.event.SelectEvent;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.faces.convert.Converter;


@Named
@Stateful
@ConversationScoped
@Loggable
public class SessionBean {
    private static final long serialVersionUID =1L;

    private Long id;
    private Session session;

    @Inject
    private Conversation conversation;

    @PersistenceContext(name="primary", type = PersistenceContextType.EXTENDED)
    private EntityManager em;


    @Resource
    SessionContext sessionContext;


    private int pageSize;
    private long count;
    
    private List<Session>  pageItems;
    private Session example = new Session();
    
    

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
            this.session = this.example;
        }
        else
        {
            this.session = findById(getId());
        }
    }

    public String update()
    {
        this.conversation.end();
        try {
            if (this.id == null)
            {
                this.em.persist(this.session);
                return "search?faces-redirect=true";
            } else {
                this.em.merge(this.session);
                return "view?faces-redirect=true&id=" + this.session.getId();
            }
        } catch (Exception e ) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }
    
    public String delete() {
        this.conversation.end();

        try {
            Session deletableEntity = findById(getId());

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

    public Session findById(Long id) { return this.em.find(Session.class, id); }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<Session> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<Session> pageItems) {
		this.pageItems = pageItems;
	}

	public Session getExample() {
		return example;
	}

	public void setExample(Session example) {
		this.example = example;
	}


    public void paginate () {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<Session> root = countCriteria.from(Session.class);
        countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
        this.setCount(this.em.createQuery(countCriteria).getSingleResult());
        CriteriaQuery<Session> criteria = builder.createQuery(Session.class);
        root = criteria.from(Session.class);
        TypedQuery<Session> query = this.em.createQuery(criteria.select(root).where(getSearchPredicates(root)));
        this.pageItems = query.getResultList();
    }


    public List<Session> getAll() {
        CriteriaQuery<Session> criteriaQuery = this.em
                .getCriteriaBuilder().createQuery(Session.class);
        return this.em.createQuery(
                criteriaQuery.select(criteriaQuery.from(Session.class))).getResultList();
    }

    private Predicate[] getSearchPredicates(Root<Session> root) {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        LocalDate sessionDate = this.example.getSessionDate();
        if (sessionDate != null)
        {
        	//TODO: come up with predicate logic.
        	//            predicatesList.add(builder.like(builder.lower(root.<String> get("email")), '%' + email.toLowerCase() + '%'));
        }


        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }



    public Converter getConverter(){

        final SessionBean ejbProxy = this.sessionContext.getBusinessObject(SessionBean.class);
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

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	private Session add = new Session ();
	
	public Session getAdd() {
		return this.add;
	}

	public Session getAdded() {
		Session added = this.add;
		this.add = new Session();
		return added;
	}

	public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
	
	// ------------------------------------------------------------------------

    // Pagination Support
    // ------------------------------------------------------------------------

   
    public int getPage() { return pageSize; }

    public void setPage(int page) { this.pageSize = page; }



}
