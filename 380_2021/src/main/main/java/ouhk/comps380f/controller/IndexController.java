package ouhk.comps380f.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController {

    @GetMapping
    public String index() {
        return "redirect:/food/item";
    }

    @GetMapping("/login")
    public String login() {
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
    
}
