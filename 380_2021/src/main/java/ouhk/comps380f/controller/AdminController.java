
package ouhk.comps380f.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.mapper.ItemMapper;
import ouhk.comps380f.mapper.RegisterMapper;
import ouhk.comps380f.model.Item;
import ouhk.comps380f.model.Register;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private Map<Long, Register> memberDatabase = new Hashtable<>();  
    private Map<Long, Item> itemDatabase = new Hashtable<>();  
    public JdbcTemplate jdbctempele(){
        DriverManagerDataSource ds;
        ds = new DriverManagerDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        ds.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
        ds.setUrl("jdbc:derby://localhost:1527/food");
        ds.setUsername("nbuser");
        ds.setPassword("nbuser");
        return jdbcTemplate;
}
    public void selectitem(){
    JdbcTemplate jt = jdbctempele();
        String sqlSelect = "SELECT * FROM FOODLIST ";
        List<Item> Itemlist = jt.query(sqlSelect, new ItemMapper());
        for (Item lists : Itemlist) {
            this.itemDatabase.put(lists.getId(), lists);
        }
    }
    public void selectmember(){
     JdbcTemplate jt = jdbctempele();
        String sqlSelect = "SELECT* FROM users INNER JOIN user_roles ON users.username = user_roles.username and user_roles.\"ROLE\" = 'ROLE_USER'";
        List<Register> memberlist = jt.query(sqlSelect, new RegisterMapper());
        for (Register lists : memberlist) {
      
            this.memberDatabase.put(lists.getId(), lists);
        }
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
     
     
     @GetMapping(value = {"", "/page"})
    public String list(ModelMap model, HttpServletRequest request) {
      if (   (!request.isUserInRole("ROLE_ADMIN")
               )) {
                selectitem();
               model.addAttribute("itemDatabase", itemDatabase);
           
            return "redirect:/food/item";
        }
      // JdbcTemplate jt = jdbctempele();
       // String sqlSelect = "SELECT* FROM users INNER JOIN user_roles ON users.username = user_roles.username and user_roles.\"ROLE\" = 'ROLE_USER'";
        //List<Register> memberlist = jt.query(sqlSelect, new RegisterMapper());
        //for (Register lists : memberlist) {
         //System.out.println("id = " + lists.getId());
           // System.out.println("foodname" + lists.getFoodname());
        //    this.memberDatabase.put(lists.getId(), lists);
        //}
        //System.out.println("map" + memberDatabase);
        //this.memberDatabase.put(lists.getId(), lists);
        selectmember();
        // jdbcTemplate.queryForObject("SELECT * FROM FOODLIST","",memberDatabase );
        model.addAttribute("memberDatabase", memberDatabase);
        return "admin";
    }
      @GetMapping("/member/edit/{memberId}")
    public ModelAndView showEdit(@PathVariable("memberId") long memberId,
            Principal principal, HttpServletRequest request, ModelMap model) {
        
        if (
              (!request.isUserInRole("ROLE_ADMIN")
               )) {
          selectitem();
               model.addAttribute("itemDatabase", itemDatabase);
          return new ModelAndView(new RedirectView("/food/item", true));
        }
        // Map<Long, Register> userDatabase = new Hashtable<>();  
      //   JdbcTemplate jt = jdbctempele();
       // String sqlSelect = "SELECT* FROM users INNER JOIN user_roles ON users.username = user_roles.username and user_roles.\"ROLE\" = 'ROLE_USER'";
       // List<Register> memberlist = jt.query(sqlSelect, new RegisterMapper());
        //for (Register lists : memberlist) {
         //System.out.println("id = " + lists.getId());
           // System.out.println("foodname" + lists.getFoodname());
         //   this.memberDatabase.put(lists.getId(), lists);
       // }
       selectmember();
        Register member = memberDatabase.get(memberId);
        if (member == null
                || (!request.isUserInRole("ROLE_ADMIN")
               )) {
             selectitem();
               model.addAttribute("itemDatabase", itemDatabase);
            return new ModelAndView(new RedirectView("/food/item/list", true));
        }
        ModelAndView modelAndView = new ModelAndView("member");
        modelAndView.addObject("memberId", Long.toString(memberId));
        modelAndView.addObject("member", member);
        AdminController.Form memberForm = new AdminController.Form();
        memberForm.setUsername(member.getUsername());
        memberForm.setPassword(member.getPassword());
        memberForm.setPhonenumber(member.getPhonenumber());
        memberForm.setFullname(member.getFullname());
        memberForm.setDelivery_address(member.getDelivery_address());
        modelAndView.addObject("memberForm", memberForm);
        model.addAttribute("memberDatabase", memberDatabase);
        return modelAndView;
    }

    @PostMapping("/member/edit/{memberId}")
    public String edit(@PathVariable("memberId") long memberId, AdminController.Form form,
            Principal principal, HttpServletRequest request, ModelMap model)
            throws IOException {
        if (
              (!request.isUserInRole("ROLE_ADMIN")
               )) {
          selectitem();
               model.addAttribute("itemDatabase", itemDatabase);
          //return new ModelAndView(new RedirectView("/food/item", true));
           return "redirect:/food/item";
        }
        JdbcTemplate jt = jdbctempele();
      
       selectmember();
        Register member = this.memberDatabase.get(memberId);
   
        

           String sql1 = "UPDATE USER_ROLES SET USERNAME = '" +form.getUsername()+ 
            "' WHERE userid = "+memberId;
            String sql2 = "UPDATE USERS SET USERNAME ='"+form.getUsername()+ 
            "', PASSWORD = '" +form.getPassword() +
            "',  FULLNAME ='" +form.getFullname()+ 
            "', PHONENUMBER ="+ form.getPhonenumber() +
            ", DELIVERY_ADDRESS = '"+form.getDelivery_address() + 
            "' WHERE userid = "+memberId;
            String sql6 = "UPDATE COMMENTS SET USERNAME = '" +form.getUsername()+ 
            "' WHERE userid = "+memberId;
             String sql3 = "UPDATE SHOPHIST SET USERNAME = '" +form.getUsername()+ 
            "' WHERE userid = "+memberId;
              String sql4 = "UPDATE SHOPCART SET USERNAME = '" +form.getUsername()+ 
            "' WHERE userid = "+memberId;
               String sql5 = "UPDATE FAVORITE SET USERNAME = '" +form.getUsername()+ 
            "' WHERE userid = "+memberId;
            jt.update(sql3);
            jt.update(sql5);
            jt.update(sql4);
            jt.update(sql6);
            jt.update(sql1);
            jt.update(sql2);
        //}
         
      
        
      
     
        //this.memberDatabase.put(member.getId(), member);
        //return modelAndView;
        return "redirect:/admin";
    }
    @GetMapping("/member/delete/{memberId}")
    public String deleteTicket(@PathVariable("memberId") long memberId,ModelMap model, HttpServletRequest request) {
         if (
              (!request.isUserInRole("ROLE_ADMIN")
               )) {
          selectitem();
               model.addAttribute("itemDatabase", itemDatabase);
          //return new ModelAndView(new RedirectView("/food/item", true));
           return "redirect:/food/item";
        }
        JdbcTemplate jt = jdbctempele();
   
        selectmember();
        if (this.memberDatabase.containsKey(memberId)) {
           String sql = "DELETE FROM USER_ROLES WHERE USERID = " + memberId;
           String sql2 = "DELETE FROM USERS WHERE USERID = " + memberId;
           String sql3 = "DELETE FROM SHOPHIST WHERE USERID = " + memberId;
           String sql4 = "DELETE FROM FAVORITE WHERE USERID = " + memberId;
           String sql5 = "DELETE FROM SHOPCART WHERE USERID = " + memberId;
           String sql6 = "DELETE FROM COMMENTS WHERE USERID = " + memberId;

           jt.update(sql4);
           jt.update(sql3);
           jt.update(sql5);
           jt.update(sql6);
           jt.update(sql);
           jt.update(sql2);
            this.memberDatabase.remove(memberId);
        }
        //return new ModelAndView(new RedirectView("/food/member",));
        //return new RedirectView("");
        model.addAttribute("memberDatabase", memberDatabase);
        return "admin";
       //return "redirect:/food/member";
    }
    
    
}
