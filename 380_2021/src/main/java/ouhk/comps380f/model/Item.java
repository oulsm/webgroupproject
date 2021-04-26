package ouhk.comps380f.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class Item implements Serializable {

    private long id;
    private String foodname;
    private String description;
    private Double price;
    private long noffood;
    private Map<String, Comments> comments = new Hashtable<>();
    private Map<String, Attachment> attachments = new Hashtable<>();

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

   

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public long getNoffood() {
        return noffood;
    }

    public void setNoffood(long noffood) {
        this.noffood = noffood;
    }

    public Map<String, Comments> getComments() {
        return comments;
    }

    public void setComments(Map<String, Comments> comments) {
        this.comments = comments;
    }

     public Attachment getAttachment(String name) {
        return this.attachments.get(name);
    }

    public Collection<Attachment> getAttachments() {
        return this.attachments.values();
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.put(attachment.getName(), attachment);
    }

    public int getNumberOfAttachments() {
        return this.attachments.size();
    }

    public boolean hasAttachment(String name) {
        return this.attachments.containsKey(name);
    }

    public Attachment deleteAttachment(String name) {
        return this.attachments.remove(name);
    }

}
