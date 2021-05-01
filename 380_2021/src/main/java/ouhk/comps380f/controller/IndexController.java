package ouhk.comps380f.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.mapper.RegisterMapper;
import ouhk.comps380f.model.Register;

@Controller
public class IndexController {

    @GetMapping
    public String index() {
        return "redirect:/food/item";
    }

    @GetMapping("/login")
    public String login() {
          JdbcTemplate jt = jdbctempele();
 
        String sql5 = "DELETE FROM SHOPCART " ;
        jt.update(sql5);
        return "login";
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    @PostMapping("/goreg/1")
    public ModelAndView registers() {
        return new ModelAndView(new RedirectView("/register", true));
    }
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
 

        
    
    
}
