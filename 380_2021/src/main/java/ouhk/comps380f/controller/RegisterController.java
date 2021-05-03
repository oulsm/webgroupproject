
package ouhk.comps380f.controller;

//import com.ibm.dtfj.corereaders.Register;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
import ouhk.comps380f.mapper.RegisterMapper;
import ouhk.comps380f.model.Register;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private Map<Long, Register> custDatabase = new Hashtable<>();

    public JdbcTemplate jdbctempele() {
        DriverManagerDataSource ds;
        ds = new DriverManagerDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        ds.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
        ds.setUrl("jdbc:derby://localhost:1527/food");
        ds.setUsername("nbuser");
        ds.setPassword("nbuser");
        return jdbcTemplate;
    }

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
        private Long phonenumber;
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
    }

    public static class CForm {

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

    public void selectmember() {
        JdbcTemplate jt = jdbctempele();
        String sqlSelect = "SELECT* FROM users INNER JOIN user_roles ON users.username = user_roles.username and user_roles.\"ROLE\" = 'ROLE_USER'";
        List<Register> memberlist = jt.query(sqlSelect, new RegisterMapper());
        for (Register lists : memberlist) {
            this.custDatabase.put(lists.getId(), lists);
        }
    }

    @PostMapping("1")
    public RedirectView insert(Form form, Principal principal, HttpServletRequest request, ModelMap model) throws SQLException, IOException {
        Register custdata = new Register();
          selectmember();
        //  custdata.setId(this.getNextItemId());
        //custdata.setCustomerName(principal.getName());
        //  Map<String, Register> tempDatabase = new Hashtable<>();
        custdata.setUsername(form.getUsername());
        custdata.setPassword(form.getPassword());
        custdata.setFullname(form.getFullname());
        custdata.setPhonenumber(form.getPhonenumber());
        custdata.setDelivery_address(form.getDelivery_address());
        JdbcTemplate jt = jdbctempele();
        Boolean username = false;
          
        for (Object key : custDatabase.keySet()) {
             System.out.println(key + " : " + custDatabase.get(key).getUsername());
            if (custDatabase.get(key).getUsername().equals(form.getUsername())) {
              //   ModelAndView modelAndView = new ModelAndView("register");
                
                username = true;   
                //model.addAttribute("repeat", 4);
                //return new ModelAndView("register", "custdataForm", new Form());                
                return new RedirectView("/register/1", true);
                //return modelAndView;
                //return new ModelAndView(new RedirectView("/register", true));
               // return new ModelAndView("register", "custdataForm", new Form());
            }
        }

        if (!username) {
            String password = "{noop}" + form.getPassword();

            //this.custDatabase.put("1", custdata);
            //System.out.println(custDatabase);
            jt.update(
                    "INSERT INTO USERS(username,password,fullname,phonenumber,delivery_address) VALUES (?, ?, ?, ?, ?)",
                    form.getUsername(),
                    password, form.getFullname(),
                    form.getPhonenumber(),
                    form.getDelivery_address());

            String sqlSelect = "SELECT* FROM users WHERE username = '" + form.getUsername() + "'";

            List<Register> memberlist = jt.query(sqlSelect, new RegisterMapper());
            for (Register lists : memberlist) {
                //   System.out.println("id = " + lists.getId());
                // System.out.println("foodname" + lists.getFoodname());
                // this.tempDatabase.put(lists.getId(), lists);
                jt.update(
                        "INSERT INTO USER_ROLES(userid, username, role) VALUES (?, ?,?)", lists.getId(), form.getUsername(), "ROLE_USER");

            }
            if ((request.isUserInRole("ROLE_ADMIN"))) {
               // ModelAndView modelAndView = new ModelAndView("admin");
                
               // return modelAndView;
                return new RedirectView("/admin", true);
            }
              ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("success", true);
            // this.itemDatabase.put(item.getId(), item);
        }
        // ModelAndView modelAndView = new ModelAndView("login");
     //   return modelAndView;
        return new RedirectView("/login", true);

    }

}
