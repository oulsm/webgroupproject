
package ouhk.comps380f.model;

public class Shopcart {
    private long cartid;
    private long foodid;
    private long userid;
    private String username;
    private String foodname;
    private Double price;
    private Long noffood;

    public long getCartid() {
        return cartid;
    }

    public void setCartid(long cartid) {
        this.cartid = cartid;
    }

    public long getFoodid() {
        return foodid;
    }

    public void setFoodid(long foodid) {
        this.foodid = foodid;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getNoffood() {
        return noffood;
    }

    public void setNoffood(Long noffood) {
        this.noffood = noffood;
    }
    
}
