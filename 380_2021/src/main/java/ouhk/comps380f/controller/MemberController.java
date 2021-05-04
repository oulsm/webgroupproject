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
import ouhk.comps380f.mapper.ShopcartMapper;
import ouhk.comps380f.mapper.RegisterMapper;
import ouhk.comps380f.mapper.FavoriteMapper;
import ouhk.comps380f.mapper.ShophistMapper;
import ouhk.comps380f.model.Favorite;
import ouhk.comps380f.model.Shopcart;
import ouhk.comps380f.model.Item;
import ouhk.comps380f.model.Register;
import ouhk.comps380f.model.Shopcart;
import ouhk.comps380f.model.Shophist;

@Controller
@RequestMapping("/member")
public class MemberController {

    private Map<Long, Item> itemDatabase = new Hashtable<>();
    private Map<Long, Register> memberDatabase = new Hashtable<>();
    private Map<Long, Shopcart> cartDatabase = new Hashtable<>();
    private Map<Long, Favorite> favDatabase = new Hashtable<>();
    private Map<Long, Shophist> histDatabase = new Hashtable<>();

    private Long memberId;

    
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
 public void selectitem(){
    JdbcTemplate jt = jdbctempele();
        String sqlSelect = "SELECT * FROM FOODLIST ";
        List<Item> Itemlist = jt.query(sqlSelect, new ItemMapper());
        for (Item lists : Itemlist) {
            this.itemDatabase.put(lists.getId(), lists);
        }
    }
    public static class SForm {

        private Boolean checkout = true;

        public Boolean getCheckout() {
            return checkout;
        }

        public void setCheckout(Boolean checkout) {
            this.checkout = checkout;
        }

    }

    @GetMapping(value = {"", "/home/{m_name}"}) 
    public ModelAndView home(@PathVariable("m_name") String m_name, ModelMap model, Principal principal,
            HttpServletRequest request) {
        if (m_name == null || !principal.getName().equals(m_name)) {
             selectitem();
               //model.addAttribute("itemDatabase", itemDatabase);
            model.addAttribute("principal", principal.getName());
            return new ModelAndView(new RedirectView("/food/item", true));
        }
        JdbcTemplate jt = jdbctempele();
        String sqlSelect = "SELECT* FROM users WHERE username ='" + m_name + "'";
        List<Register> memberlist = jt.query(sqlSelect, new RegisterMapper());
        for (Register lists : memberlist) {
            this.memberDatabase.put(lists.getId(), lists);
            this.memberId = lists.getId();
        }
        
        
        Register member = memberDatabase.get(memberId);
       
       
        System.out.println("member" + member.getPassword());
        ModelAndView modelAndView = new ModelAndView("mhome");
        modelAndView.addObject("memberId", Long.toString(memberId));
        modelAndView.addObject("member", member);
        //System.out.println("map" + memberDatabase);
        //this.memberDatabase.put(lists.getId(), lists);

        // jdbcTemplate.queryForObject("SELECT * FROM FOODLIST","",memberDatabase );
        model.addAttribute("memberDatabase", memberDatabase);
        return modelAndView;
    }

    @GetMapping("/edit/{m_name}")
    public ModelAndView showEdit(@PathVariable("m_name") String m_name,
            Principal principal, HttpServletRequest request, ModelMap model) {
        // Map<Long, Register> userDatabase = new Hashtable<>();  
         if (m_name == null || !principal.getName().equals(m_name)) {
             selectitem();
               //model.addAttribute("itemDatabase", itemDatabase);
            model.addAttribute("principal", principal.getName());
            return new ModelAndView(new RedirectView("/food/item", true));
        }
       JdbcTemplate jt = jdbctempele();
        String sqlSelect = "SELECT* FROM users WHERE username ='" + m_name + "'";
        List<Register> memberlist = jt.query(sqlSelect, new RegisterMapper());
        for (Register lists : memberlist) {
            this.memberDatabase.put(lists.getId(), lists);
            this.memberId = lists.getId();
        }
         Register member = memberDatabase.get(memberId);
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

    @PostMapping("/edit/{m_name}")
    public String edit(@PathVariable("m_name") String m_name, AdminController.Form form,
            Principal principal, HttpServletRequest request, ModelMap model)
            throws IOException {
         if (m_name == null || !principal.getName().equals(m_name)) {
             selectitem();
               //model.addAttribute("itemDatabase", itemDatabase);
            model.addAttribute("principal", principal.getName());
            //return new ModelAndView(new RedirectView("/food/item", true));
            return "redirect:/food/item/";
        }
       JdbcTemplate jt = jdbctempele();
        String sqlSelect = "SELECT* FROM users WHERE username ='" + m_name + "'";
        List<Register> memberlist = jt.query(sqlSelect, new RegisterMapper());
        for (Register lists : memberlist) {
            this.memberDatabase.put(lists.getId(), lists);
            this.memberId = lists.getId();
        }
         Register member = memberDatabase.get(memberId);
        //member.setNoffood(form.getNoffood());
        //if ((!request.isUserInRole("ROLE_ADMIN"))) {

            String sql = "UPDATE USERS SET PASSWORD = '" + form.getPassword()
                    + "',  FULLNAME ='" + form.getFullname()
                    + "', PHONENUMBER =" + form.getPhonenumber()
                    + ", DELIVERY_ADDRESS = '" + form.getDelivery_address()
                    + "' WHERE userid = " + memberId;

            jt.update(sql);

       /* } else {

            String sql1 = "UPDATE USER_ROLES SET USERNAME = '" + form.getUsername()
                    + "' WHERE userid = " + memberId;
            String sql2 = "UPDATE USERS SET USERNAME ='" + form.getUsername()
                    + "', PASSWORD = '" + form.getPassword()
                    + "',  FULLNAME ='" + form.getFullname()
                    + "', PHONENUMBER =" + form.getPhonenumber()
                    + ", DELIVERY_ADDRESS = '" + form.getDelivery_address()
                    + "' WHERE userid = " + memberId;
            jt.update(sql1);
            jt.update(sql2);
        }
*/
        //this.memberDatabase.put(member.getId(), member);
        //return modelAndView;
        return "redirect:/member/home/" + member.getUsername();
    }

    @GetMapping("/shopcart/{m_name}")
    public ModelAndView view(@PathVariable("m_name") String m_name, Principal principal,
            ModelMap model) {
        if (m_name == null || !principal.getName().equals(m_name)) {
             selectitem();
               //model.addAttribute("itemDatabase", itemDatabase);
            model.addAttribute("principal", principal.getName());
            return new ModelAndView(new RedirectView("/food/item", true));
           //return "redirect:/food/item/";
        }
        JdbcTemplate jt = jdbctempele();
        String sqlSelect = "SELECT* FROM users WHERE username ='" + m_name + "'";
        List<Register> memberlist = jt.query(sqlSelect, new RegisterMapper());
        for (Register lists : memberlist) {
            this.memberDatabase.put(lists.getId(), lists);
            this.memberId = lists.getId();
        }
        Register member = this.memberDatabase.get(memberId);
        // Map<Long, Shopcart> commentDatabase = new Hashtable<>();
        // Item item = this.cartDatabase.get(itemId);
        // JdbcTemplate jt = jdbctempele();
        String sqlSelect2 = "SELECT * FROM SHOPCART WHERE userid = " + member.getId();
        List<Shopcart> Shopcartlist = jt.query(sqlSelect2, new ShopcartMapper());
        for (Shopcart cart : Shopcartlist) {
            // System.out.println("id = " + comment.getId());
            // System.out.println("floor = " + comment.getFloor());
            // System.out.println("username" + comment.getUsername());
            //   System.out.println("body" + comment.getBody());
            cartDatabase.put(cart.getCartid(), cart);

        }

        // System.out.println(commentDatabase);
        model.addAttribute("cartDatabase", cartDatabase);

        //   model.addAttribute("itemId", itemId);
        //  model.addAttribute("item", item);
        //  return new ModelAndView("view", "CForm", new ItemController.CForm());
        //return "shopcart";
        // return new ModelAndView(new RedirectView("/shpcart/"+ member.getUsername(), true));
        ModelAndView modelAndView = new ModelAndView("cart");
        model.addAttribute("member", member);
        return new ModelAndView("cart", "SForm", new SForm());
    }

    @PostMapping("/shopcart/{m_name}")
    public ModelAndView view(@PathVariable("m_name") String m_name, MemberController.SForm form, Principal principal,
            ModelMap model, HttpServletRequest request) {
        if (m_name == null || !principal.getName().equals(m_name)) {
             selectitem();
               //model.addAttribute("itemDatabase", itemDatabase);
            model.addAttribute("principal", principal.getName());
            return new ModelAndView(new RedirectView("/food/item", true));
           //return "redirect:/food/item/";
        }
        //  Item item = this.itemDatabase.get(itemId);
        JdbcTemplate jt = jdbctempele();
        String sqlSelect = "SELECT* FROM users WHERE username ='" + m_name + "'";
        List<Register> memberlist = jt.query(sqlSelect, new RegisterMapper());
        for (Register lists : memberlist) {
            this.memberDatabase.put(lists.getId(), lists);
            this.memberId = lists.getId();
        }
        String sqlSelect2 = "SELECT * FROM FOODLIST ORDER BY FOODID";
        List<Item> Itemlist = jt.query(sqlSelect2, new ItemMapper());
        for (Item lists : Itemlist) {
            //   System.out.println("id = " + lists.getId());
            // System.out.println("foodname" + lists.getFoodname());
            this.itemDatabase.put(lists.getId(), lists);
        }

        if (form.getCheckout() == true) {
            for (Object cart : cartDatabase.keySet()) {
                // System.out.println("id = " + comment.getId());
                // System.out.println("floor = " + comment.getFloor());
                // System.out.println("username" + comment.getUsername());
                //   System.out.println("body" + comment.getBody());
                //cartDatabase.put(cart.getCartid(), cart);
                System.out.println(cart + " cart: " + cartDatabase.get(cart).getFoodname());
                String sqlinsert = "INSERT INTO SHOPHIST(userid, username, foodid, foodname, noffood, price) VALUES (?, ?, ?, ?, ?, ?) ";
                jt.update(
                        sqlinsert,
                        cartDatabase.get(cart).getUserid(),
                        cartDatabase.get(cart).getUsername(),
                        cartDatabase.get(cart).getFoodid(),
                        cartDatabase.get(cart).getFoodname(),
                        cartDatabase.get(cart).getNoffood(),
                        cartDatabase.get(cart).getPrice()
                );
                for (Object item : itemDatabase.keySet()) {
                    if (cartDatabase.get(cart).getFoodid() == itemDatabase.get(item).getId()) {
                        String sqlupdate = "UPDATE FOODLIST SET noffood = "
                                + (itemDatabase.get(item).getNoffood() - cartDatabase.get(cart).getNoffood())
                                + " WHERE FOODID = " + itemDatabase.get(item).getId();
                        jt.update(sqlupdate);
                    }
                }

            }
            String sql5 = "DELETE FROM SHOPCART " ;
        jt.update(sql5);
        cartDatabase.clear();
        }
        
        String sqlSelect5 = "SELECT * FROM SHOPHIST WHERE userid = " + memberId;
        List<Shophist> Shophistlist = jt.query(sqlSelect5, new ShophistMapper());
        for (Shophist hist : Shophistlist) {
      
            histDatabase.put(hist.getCartid(), hist);

        }
        
        // System.out.println(commentDatabase);
        model.addAttribute("histDatabase", histDatabase);
        
        
        Register member = this.memberDatabase.get(memberId);
       
         ModelAndView modelAndView = new ModelAndView("history");
       model.addAttribute("member", member);
        return modelAndView;
        //return "redirect:/food/item";

    }

    @GetMapping("/favorite/{m_name}")
    public ModelAndView fav(@PathVariable("m_name") String m_name, Principal principal,
            ModelMap model) {
        if (m_name == null || !principal.getName().equals(m_name)) {
             selectitem();
               //model.addAttribute("itemDatabase", itemDatabase);
            model.addAttribute("principal", principal.getName());
            return new ModelAndView(new RedirectView("/food/item", true));
           //return "redirect:/food/item/";
        }
        JdbcTemplate jt = jdbctempele();
        String sqlSelect = "SELECT* FROM users WHERE username ='" + m_name + "'";
        List<Register> memberlist = jt.query(sqlSelect, new RegisterMapper());
        for (Register lists : memberlist) {
            this.memberDatabase.put(lists.getId(), lists);
            this.memberId = lists.getId();
        }
        Register member = this.memberDatabase.get(memberId);
        // Map<Long, Shopcart> commentDatabase = new Hashtable<>();
        // Item item = this.cartDatabase.get(itemId);
        // JdbcTemplate jt = jdbctempele();
        String sqlSelect2 = "SELECT * FROM FAVORITE WHERE userid = " + member.getId();
        List<Favorite> Shophistlist = jt.query(sqlSelect2, new FavoriteMapper());
        for (Favorite cart : Shophistlist) {
            // System.out.println("id = " + comment.getId());
            // System.out.println("floor = " + comment.getFloor());
            // System.out.println("username" + comment.getUsername());
            //   System.out.println("body" + comment.getBody());
            favDatabase.put(cart.getCartid(), cart);

        }

        // System.out.println(commentDatabase);
        model.addAttribute("favDatabase", favDatabase);

        //   model.addAttribute("itemId", itemId);
        //  model.addAttribute("item", item);
        //  return new ModelAndView("view", "CForm", new ItemController.CForm());
        //return "shopcart";
        // return new ModelAndView(new RedirectView("/shpcart/"+ member.getUsername(), true));
        ModelAndView modelAndView = new ModelAndView("favorite");
        model.addAttribute("member", member);
        // return new ModelAndView("cart", "SForm", new SForm());
        return modelAndView;
    }

    @GetMapping("/history/{m_name}")
    public ModelAndView his(@PathVariable("m_name") String m_name,Principal principal,
            ModelMap model) {
        if (m_name == null || !principal.getName().equals(m_name)) {
             selectitem();
               //model.addAttribute("itemDatabase", itemDatabase);
            model.addAttribute("principal", principal.getName());
            return new ModelAndView(new RedirectView("/food/item", true));
           //return "redirect:/food/item/";
        }
        JdbcTemplate jt = jdbctempele();
        String sqlSelect = "SELECT* FROM users WHERE username ='" + m_name + "'";
        List<Register> memberlist = jt.query(sqlSelect, new RegisterMapper());
        for (Register lists : memberlist) {
            this.memberDatabase.put(lists.getId(), lists);
            this.memberId = lists.getId();
        }
        Register member = this.memberDatabase.get(memberId);

        // Map<Long, Shopcart> commentDatabase = new Hashtable<>();
        // Item item = this.cartDatabase.get(itemId);
        // JdbcTemplate jt = jdbctempele();
        String sqlSelect2 = "SELECT * FROM SHOPHIST WHERE userid = " + member.getId();
        List<Shophist> Shophistlist = jt.query(sqlSelect2, new ShophistMapper());
        for (Shophist hist : Shophistlist) {
            // System.out.println("id = " + comment.getId());
            // System.out.println("floor = " + comment.getFloor());
            // System.out.println("username" + comment.getUsername());
               System.out.println("body" + hist.getOrderdate()); 
            histDatabase.put(hist.getCartid(), hist);

        }

        // System.out.println(commentDatabase);
        model.addAttribute("histDatabase", histDatabase);

        //   model.addAttribute("itemId", itemId);
        //  model.addAttribute("item", item);
        //  return new ModelAndView("view", "CForm", new ItemController.CForm());
        //return "shopcart";
        // return new ModelAndView(new RedirectView("/shpcart/"+ member.getUsername(), true));
        ModelAndView modelAndView = new ModelAndView("history");
        model.addAttribute("member", member);
        // return new ModelAndView("cart", "SForm", new SForm());
        return modelAndView;
    }

}
