package ouhk.comps380f.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class Item implements Serializable {

    private long id;
    private String foodname;
    private String description;
    private String price;
    private String noffood;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNoffood() {
        return noffood;
    }

    public void setNoffood(String noffood) {
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
