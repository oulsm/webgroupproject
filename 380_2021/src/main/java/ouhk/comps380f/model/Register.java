
package ouhk.comps380f.model;




import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

public class Register implements Serializable {
    
    private Long id;
    private String username;
    private String password;
     private Long phonenumber;
    private String fullname;
     private String delivery_address;
    //private Map<String, Custdata> data = new Hashtable<>();
     

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
 
public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Long phonenumber) {
        this.phonenumber = phonenumber;
    }
   

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

  

 

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

}
