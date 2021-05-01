package ouhk.comps380f.controller;

import ouhk.comps380f.mapper.CommentsMapper;
import ouhk.comps380f.mapper.ItemMapper;
import java.io.IOException;
import java.security.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.mapper.FavoriteMapper;
import ouhk.comps380f.mapper.RegisterMapper;
import ouhk.comps380f.mapper.ShopcartMapper;
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.Comments;
import ouhk.comps380f.model.Favorite;
import ouhk.comps380f.model.Item;
import ouhk.comps380f.model.Register;
import ouhk.comps380f.model.Shopcart;

@Controller
@RequestMapping("/food")
public class ItemController {

    private volatile long ITEM_ID_SEQUENCE = 1;
    private Map<Long, Item> itemDatabase = new Hashtable<>();
    private Map<Long, Register> memberDatabase = new Hashtable<>();
    private Map<Long, Shopcart> cartDatabase = new Hashtable<>();
    private Map<Long, Favorite> favDatabase = new Hashtable<>();
    private Long memberId;
    private Boolean gotocart;

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
    public static class Form {

        private String foodname;
        private String description;
        private Double price;
        private Long noffood;
        private List<MultipartFile> attachments;

        public String getFoodname() {
            return foodname;
        }

        public void setFoodname(String foodname) {
            this.foodname = foodname;
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

        public Long getNoffood() {
            return noffood;
        }

        public void setNoffood(Long noffood) {
            this.noffood = noffood;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }

    public static class CForm {
        private Boolean fav = true;
        private Long noffood;
        private String username;
        private String body;

        public Boolean getFav() {
            return fav;
        }

        public void setFav(Boolean fav) {
            this.fav = fav;
        }

        public Long getNoffood() {
            return noffood;
        }

        public void setNoffood(Long noffood) {
            this.noffood = noffood;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

    }

    @GetMapping(value = {"", "/item"})
    public String list(ModelMap model, Principal principal, HttpServletRequest request) {

        JdbcTemplate jt = jdbctempele();
        String sqlSelect = "SELECT * FROM FOODLIST ORDER BY FOODID";
        List<Item> Itemlist = jt.query(sqlSelect, new ItemMapper());
        for (Item lists : Itemlist) {
            this.itemDatabase.put(lists.getId(), lists);
        }

        if ((request.isUserInRole("ROLE_USER"))) {
            model.addAttribute("principal", principal.getName());
        }
        model.addAttribute("itemDatabase", itemDatabase);
        return "list";
    }

    @PostMapping(value = {"/login", "/item/view/login"})
    public ModelAndView login() {
        return new ModelAndView(new RedirectView("/login", true));
    }

    @GetMapping("/create")
    public ModelAndView create() {

        return new ModelAndView("add", "itemForm", new Form());
    }

    @PostMapping("/create")
    public View create(Form form, Principal principal) throws IOException {
        Item item = new Item();;

        for (MultipartFile filePart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {
                item.addAttachment(attachment);
            }
        }
        JdbcTemplate jt = jdbctempele();

        jt.update(
                "INSERT INTO FOODLIST(foodname, description, price, noffood) VALUES (?, ?, ?, ?)", 
                form.getFoodname(), form.getDescription(), form.getPrice(), form.getNoffood());

        return new RedirectView("item");

    }

    @GetMapping("/item/view/{itemId}")
    public ModelAndView view(@PathVariable("itemId") long itemId,
            ModelMap model,HttpServletRequest request, Principal principal) {
        Map<Long, Comments> commentDatabase = new Hashtable<>();
        Item item = this.itemDatabase.get(itemId);
        if (item == null) {

            return new ModelAndView(new RedirectView("/food/list", true));
        }
        JdbcTemplate jt = jdbctempele();
        String sqlSelect = "SELECT * FROM COMMENTS WHERE FOODID = " + itemId;
        List<Comments> Commentslist = jt.query(sqlSelect, new CommentsMapper());
        for (Comments comment : Commentslist) {
            commentDatabase.put(comment.getFloor(), comment);
        }

         if ((request.isUserInRole("ROLE_USER"))) {
            model.addAttribute("member", principal.getName());
        }
        model.addAttribute("commentDatabase", commentDatabase);

        model.addAttribute("itemId", itemId);
        model.addAttribute("item", item);
        return new ModelAndView("view", "CForm", new CForm());

    }

    @PostMapping("/item/view/{itemId}")
    public ModelAndView viewinsert(@PathVariable("itemId") long itemId, CForm form, Principal principal,
            HttpServletRequest request, ModelMap model) {
        Map<Long, Comments> commentDatabase = new Hashtable<>();
        this.gotocart = false;
        JdbcTemplate jt = jdbctempele();
        String sqlSelect = "SELECT* FROM users WHERE username ='" + principal.getName() + "'";
            List<Register> memberlist = jt.query(sqlSelect, new RegisterMapper());
            for (Register lists : memberlist) {
                System.out.println("id = " + lists.getId());
                System.out.println("membername" + lists.getUsername());
                this.memberDatabase.put(lists.getId(), lists);
                this.memberId = lists.getId();
         
            }
        Register member = memberDatabase.get(memberId);
        Item item = this.itemDatabase.get(itemId);
        if (item == null) {
              selectitem();
               model.addAttribute("itemDatabase", itemDatabase);
            return new ModelAndView(new RedirectView("/food/list", true));
        }
        if (form.getNoffood() == null  && form.getFav() == true && form.getBody() != null ) {
             System.out.println("comment get");
            jt.update(
                    "INSERT INTO COMMENTS(foodid, username, body, userid) VALUES (?, ?, ?, ?)", 
                    itemId, principal.getName(), form.getBody(), member.getId());
            String sqlc = "SELECT * FROM COMMENTS WHERE FOODID = " + itemId;
            List<Comments> Commentslist = jt.query(sqlc, new CommentsMapper());
            for (Comments comment : Commentslist) {
                commentDatabase.put(comment.getFloor(), comment);
            }
            System.out.println("comment get");
        } else if(form.getBody() == null  && form.getFav() == true && form.getNoffood() != null) {
             System.out.println("cart get");
    
            String sqlSelect2 = "SELECT * FROM SHOPCART ";
            List<Shopcart> Shopcartlist = jt.query(sqlSelect2, new ShopcartMapper());
            for (Shopcart cart : Shopcartlist) {
                cartDatabase.put(cart.getCartid(), cart);
            }
            Boolean shopupdate = false;
            for (Object cart : cartDatabase.keySet()) {
                System.out.println("fcart foodid = " + cartDatabase.get(cart).getFoodid());

                System.out.println("fcart userid = " + cartDatabase.get(cart).getUserid());

                if (cartDatabase.get(cart).getFoodid() == itemId && cartDatabase.get(cart).getUserid() == member.getId()) {
                    String sqlupdate = "UPDATE SHOPCART SET noffood = "
                            + (form.getNoffood() + cartDatabase.get(cart).getNoffood())
                            + " WHERE FOODID = " + itemId
                            + "AND USERID =" + member.getId();
                    jt.update(sqlupdate);
                    shopupdate = true;
                }
            }
            if (!shopupdate) {
                jt.update(
                        "INSERT INTO SHOPCART(userid , username , foodid, foodname, noffood, price) VALUES (?, ?, ?, ?, ?, ?)",
                        member.getId(), principal.getName(), itemId, item.getFoodname(), form.getNoffood(), item.getPrice());
            }
           
      
        String sqlSelect3 = "SELECT * FROM COMMENTS WHERE FOODID = " + itemId;
        List<Comments> Commentslist = jt.query(sqlSelect3, new CommentsMapper());
        for (Comments comment : Commentslist) {
            commentDatabase.put(comment.getFloor(), comment);
        }
       
            model.addAttribute("gotocart", true);
        }else if(form.getFav() == true)
        {                   
              System.out.println("favorite get");
           String sqlSelect2 = "SELECT * FROM FAVORITE ";
            List<Favorite> Shopcartlist = jt.query(sqlSelect2, new FavoriteMapper());
            for (Favorite cart : Shopcartlist) {
                favDatabase.put(cart.getCartid(), cart);
            }
            Boolean shopupdate = false;
            
            for (Object cart : favDatabase.keySet()) {
                

                if (favDatabase.get(cart).getFoodid() == itemId && favDatabase.get(cart).getUserid() == member.getId()) {
                 System.out.println("fooddata id:" + favDatabase.get(cart).getFoodid());
                 System.out.println("food id:" + itemId);
                  System.out.println("memberdata id:" + favDatabase.get(cart).getFoodid());
                   System.out.println("member id:" + member.getId());
                    shopupdate = true;
                }
            }

            if (!shopupdate) {
                String sqlinsert = "INSERT INTO FAVORITE (userid, username, foodid, foodname, price) VALUES (?, ?, ?, ?, ?) ";
              jt.update(sqlinsert, member.getId(), principal.getName(), itemId, item.getFoodname(), item.getPrice());
               
            }
            model.addAttribute("addfav", true);
            
        String sqlSelect3 = "SELECT * FROM COMMENTS WHERE FOODID = " + itemId;
        List<Comments> Commentslist = jt.query(sqlSelect3, new CommentsMapper());
        for (Comments comment : Commentslist) {
            commentDatabase.put(comment.getFloor(), comment);
        }
        }
        
        model.addAttribute("commentDatabase", commentDatabase);
        model.addAttribute("itemId", itemId);
        model.addAttribute("item", item);
        if ((request.isUserInRole("ROLE_USER"))) {
            model.addAttribute("member", principal.getName());
        }
        return new ModelAndView("view", "CForm", new CForm());

    }

    @GetMapping("/item/edit/{itemId}")
    public ModelAndView showEdit(@PathVariable("itemId") long itemId,
            Principal principal, HttpServletRequest request) {
        Item item = this.itemDatabase.get(itemId);
        if ((!request.isUserInRole("ROLE_ADMIN")) ||item == null
                ) {
            return new ModelAndView(new RedirectView("/food/item", true));
        }
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("itemId", Long.toString(itemId));
        modelAndView.addObject("item", item);
        Form itemForm = new Form();
        itemForm.setNoffood(item.getNoffood());
        itemForm.setDescription(item.getDescription());
        itemForm.setPrice(item.getPrice());
        itemForm.setFoodname(item.getFoodname() );;
        modelAndView.addObject("itemForm", itemForm);
        return modelAndView;
    }

    @PostMapping("/item/edit/{itemId}")
    public String edit(@PathVariable("itemId") long itemId, Form form, CForm Cform,
            Principal principal, HttpServletRequest request, ModelMap model)
            throws IOException {
     
        String sql = "UPDATE FOODLIST SET NOFFOOD = " + form.getNoffood() + 
                ", DESCRiPTION = '"+ form.getDescription() +
                "', FOODNAME = '"+ form.getFoodname() +
                "', PRICE ="+ form.getPrice() +
                " WHERE FOODID = " + itemId;
        JdbcTemplate jt = jdbctempele();
        jt.update(sql);
         for (Object cart : cartDatabase.keySet()) {
             this.itemDatabase.remove(cart);
         }
  
        selectitem();
        return "redirect:/food/item/view/" + itemId;
    }

    @GetMapping("/item/delete/{itemId}")
    public ModelAndView deleteTicket(@PathVariable("itemId") long itemId, HttpServletRequest request) {
        JdbcTemplate jt = jdbctempele();
        Item item = this.itemDatabase.get(itemId);
        if ((!request.isUserInRole("ROLE_ADMIN")) ||item == null
                ) {
            return new ModelAndView(new RedirectView("/food/item", true));
        }
        if (this.itemDatabase.containsKey(itemId)) {
            String sql = "DELETE FROM FOODLIST WHERE FOODID = " + itemId;
            String sql2 = "DELETE FROM COMMENTS WHERE FOODID = " + itemId;
            String sql3 = "DELETE FROM SHOPCART WHERE FOODID = " + itemId;
             jt.update(sql3);
            jt.update(sql2);
            jt.update(sql);
            this.itemDatabase.remove(itemId);
        }

         Integer Delete = 1; 
        ModelAndView modelAndView = new ModelAndView("delete");
        modelAndView.addObject("delete", Delete);

        return modelAndView;
 
    }
     @GetMapping("/comment/delete/{itemId}/{floor}")
    public ModelAndView deleteTicket(@PathVariable("itemId") long itemId,@PathVariable("floor") long floor, HttpServletRequest request) {
        JdbcTemplate jt = jdbctempele();
        Item item = this.itemDatabase.get(itemId);
        if ((!request.isUserInRole("ROLE_ADMIN")) ||item == null
                ) {
            return new ModelAndView(new RedirectView("/food/item", true));
        }
        if (this.itemDatabase.containsKey(itemId)) {
           // String sql = "DELETE FROM FOODLIST WHERE FOODID = " + itemId;
            String sql2 = "DELETE FROM COMMENTS WHERE FOODID = " + itemId + "AND FLOOR = " + floor;

            jt.update(sql2);
           // jt.update(sql);
        }
      
        Integer Delete = 2; 
        ModelAndView modelAndView = new ModelAndView("delete");
        modelAndView.addObject("delete", Delete);
        modelAndView.addObject("itemId", itemId);

    
        return modelAndView;
    }

}
