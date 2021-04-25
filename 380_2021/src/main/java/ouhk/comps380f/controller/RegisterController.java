/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ouhk.comps380f.controller;

//import com.ibm.dtfj.corereaders.Register;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;
import javax.activation.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.model.Register;



@Controller
@RequestMapping("/register")
public class RegisterController {

    private Map<String, Register> custDatabase = new Hashtable<>();
     
    public String list(ModelMap model) {
        model.addAttribute("custDatabase", custDatabase);
        return "register";
    }
    @GetMapping("/1")
      public ModelAndView inserts() {
        return new ModelAndView("register", "custdataForm", new Form());
    }

    public static class Form {

        private String username;
        private String password;
        private String phonenumber;
        private String fullname;
        private String delivery_address;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

       

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
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

    }

     @PostMapping("1")
    public RedirectView insert( Form form, Principal principal) throws SQLException, IOException {
        Register custdata = new Register();
       //  custdata.setId(this.getNextItemId());
         //custdata.setCustomerName(principal.getName());

      custdata.setUsername(form.getUsername());
        custdata.setPassword(form.getPassword());
       custdata.setFullname(form.getFullname());
        custdata.setPhonenumber(form.getPhonenumber());
        custdata.setDelivery_address(form.getDelivery_address());
        DriverManagerDataSource ds;
        ds= new DriverManagerDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        ds.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
        ds.setUrl("jdbc:derby://localhost:1527/food");
        ds.setUsername("nbuser");
        ds.setPassword("nbuser");
       
        String password = "{noop}" + form.getPassword();
        this.custDatabase.put("1", custdata);
        System.out.println(custDatabase);
        jdbcTemplate.update(
          "INSERT INTO USERS VALUES (?, ?, ?, ?, ?)",  form.getUsername() ,password, form.getFullname() , form.getPhonenumber() , form.getDelivery_address() );
        jdbcTemplate.update(
          "INSERT INTO USER_ROLES(username, role) VALUES (?,?)",   form.getUsername(), "ROLE_USER" );

        // this.itemDatabase.put(item.getId(), item);
        return new RedirectView("/login", true);
    }
   
      
  
   
}