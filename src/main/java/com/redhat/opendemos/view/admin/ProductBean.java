package com.redhat.opendemos.view.admin;


import com.redhat.opendemos.model.Product;
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
public class ProductBean
{
    private static final long serialVersionUID =1L;

    private Long id;
    private Product product;

    @Inject
    private Conversation conversation;

    @PersistenceContext(name="primary", type = PersistenceContextType.EXTENDED)
    private EntityManager em;


    @Resource
    SessionContext sessionContext;


    private int pageSize;
    private long count;
    private List<Product> pageItems;
    private Product example = new Product();


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
            this.product = this.example;
        }
        else
        {
            this.product = findById(getId());
        }
    }

    public String update()
    {
        this.conversation.end();
        try {
            if (this.id == null)
            {
                this.em.persist(this.product);
                return "search?faces-redirect=true";
            } else {
                this.em.merge(this.product);
                return "view?faces-redirect=true&id=" + this.product.getId();
            }
        } catch (Exception e ) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    public String delete() {
        this.conversation.end();

        try {
            Product deletableEntity = findById(getId());

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

    public Product findById(Long id) { return this.em.find(Product.class, id); }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }

    public void setProduct(Product product) { this.product = product; }



    // ------------------------------------------------------------------------

    // Pagination Support
    // ------------------------------------------------------------------------

    public int getPageSize() { return pageSize; }

    public void setPageSize(int page) { this.pageSize = page; }

    public int getPage() { return pageSize; }

    public void setPage(int page) { this.pageSize = page; }


    public long getCount() { return count; }

    public void setCount(long count) { this.count = count; }

    public List<Product> getPageItems() { return pageItems; }

    public void setPageItems(List<Product> pageItems) { this.pageItems = pageItems; }

    public Product getExample() { return example; }

    public void setExample(Product example) { this.example = example; }

    public String search () {
        this.pageSize = 0;
         return null;
    }

    public void paginate () {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<Product> root = countCriteria.from(Product.class);
        countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
        this.count = this.em.createQuery(countCriteria).getSingleResult();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        root = criteria.from(Product.class);
        TypedQuery<Product> query = this.em.createQuery(criteria.select(root).where(getSearchPredicates(root)));
        this.pageItems = query.getResultList();
    }


    public List<Product> getAll() {
        CriteriaQuery<Product> criteriaQuery = this.em
                .getCriteriaBuilder().createQuery(Product.class);
        return this.em.createQuery(
                criteriaQuery.select(criteriaQuery.from(Product.class))).getResultList();
    }

    private Predicate[] getSearchPredicates(Root<Product> root) {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        String name = this.example.getName();
        if (name != null && "".equals(name))
        {
            predicatesList.add(builder.like(builder.lower(root.<String> get("name")), '%' + name.toLowerCase() + '%'));
        }


        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }


    public Converter getConverter(){

        final ProductBean ejbProxy = this.sessionContext.getBusinessObject(ProductBean.class);
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
                return String.valueOf(((Product) value).getId());
            }
        };
    }

    private Product add = new Product();

    public Product getAdd(){
        return this.add;
    }

    public Product getAdded()
    {
        Product added = this.add;
        this.add = new Product();
        return added;
    }
}
