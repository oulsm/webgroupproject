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
import ouhk.comps380f.mapper.RegisterMapper;
import ouhk.comps380f.mapper.ShopcartMapper;
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.Comments;
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

        private Long noffood;
        private String username;
        private String body;

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
            //   System.out.println("id = " + lists.getId());
            // System.out.println("foodname" + lists.getFoodname());
            this.itemDatabase.put(lists.getId(), lists);
        }
        // ModelAndView modelAndView = new ModelAndView("list");
        // modelAndView.addObject("principal", principal.getName());
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

    //  private volatile long ITEM_ID_SEQUENCE = 1;
    // private Map<Long, Ticket> itemDatabase = new Hashtable<>();
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
                "INSERT INTO FOODLIST(foodname, description, price, noffood) VALUES (?, ?, ?, ?)", form.getFoodname(), form.getDescription(), form.getPrice(), form.getNoffood());

        // this.itemDatabase.put(item.getId(), item);
        //return new RedirectView("/item/view/" + item.getId(), true);
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

        // System.out.println(commentDatabase);
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

        Item item = this.itemDatabase.get(itemId);
        if (item == null) {
            return new ModelAndView(new RedirectView("/food/list", true));
        }
        JdbcTemplate jt = jdbctempele();
        if (form.getNoffood() == null) {
            jt.update(
                    "INSERT INTO COMMENTS(foodid, username, body) VALUES (?, ?, ?)", itemId, principal.getName(), form.getBody());
            String sqlSelect = "SELECT * FROM COMMENTS WHERE FOODID = " + itemId;
            List<Comments> Commentslist = jt.query(sqlSelect, new CommentsMapper());
            for (Comments comment : Commentslist) {
                //   System.out.println("id = " + comment.getId());
                //   System.out.println("floor = " + comment.getFloor());
                // System.out.println("username" + comment.getUsername());
                // System.out.println("body" + comment.getBody());
                commentDatabase.put(comment.getFloor(), comment);
                //     for (Object key : commentDatabase.keySet()) {
                // System.out.println(key + " : " + commentDatabase.get(key));
                //}
            }
        } else {
            // JdbcTemplate jt = jdbctempele();
            String sqlSelect = "SELECT* FROM users WHERE username ='" + principal.getName() + "'";
            List<Register> memberlist = jt.query(sqlSelect, new RegisterMapper());
            for (Register lists : memberlist) {
                System.out.println("id = " + lists.getId());
                System.out.println("membername" + lists.getUsername());
                this.memberDatabase.put(lists.getId(), lists);
                this.memberId = lists.getId();
         
            }
            Register member = memberDatabase.get(memberId);
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
        if (item == null
                || (!request.isUserInRole("ROLE_ADMIN"))) {
            return new ModelAndView(new RedirectView("/item/list", true));
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
        Item item = this.itemDatabase.get(itemId);
        if (item == null
                || (!request.isUserInRole("ROLE_ADMIN"))) {
            //  return new ModelAndView(new RedirectView("/item/list", true));
            return "redirect:/item/list";
        }

        //item.setNoffood(form.getNoffood());
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
         String sqlSelect = "SELECT * FROM FOODLIST ORDER BY FOODID";
        List<Item> Itemlist = jt.query(sqlSelect, new ItemMapper());
        for (Item lists : Itemlist) {
            //   System.out.println("id = " + lists.getId());
            // System.out.println("foodname" + lists.getFoodname());
            this.itemDatabase.put(lists.getId(), lists);
        }
        //return modelAndView;
        return "redirect:/food/item/view/" + item.getId();
    }

    @GetMapping("/item/delete/{itemId}")
    public String deleteTicket(@PathVariable("itemId") long itemId) {
        JdbcTemplate jt = jdbctempele();
        if (this.itemDatabase.containsKey(itemId)) {
            String sql = "DELETE FROM FOODLIST WHERE FOODID = " + itemId;
            String sql2 = "DELETE FROM COMMENTS WHERE FOODID = " + itemId;

            jt.update(sql2);
            jt.update(sql);
            this.itemDatabase.remove(itemId);
        }
        //return new ModelAndView(new RedirectView("/food/item",));
        //return new RedirectView("");
        return "delete";
        //return "redirect:/food/item";
    }
     @GetMapping("/comment/delete/{itemId}/{floor}")
    public String deleteTicket(@PathVariable("itemId") long itemId,@PathVariable("floor") long floor) {
        JdbcTemplate jt = jdbctempele();
        if (this.itemDatabase.containsKey(itemId)) {
           // String sql = "DELETE FROM FOODLIST WHERE FOODID = " + itemId;
            String sql2 = "DELETE FROM COMMENTS WHERE FOODID = " + itemId + "AND FLOOR = " + floor;

            jt.update(sql2);
           // jt.update(sql);
        }
        //return new ModelAndView(new RedirectView("/food/item",));
        //return new RedirectView("");
        
        
       // return "redirect:/food/item/view/" + itemId;
        return "delete";
        //return "redirect:/food/item";
    }

    private synchronized long getNextItemId() {
        return this.ITEM_ID_SEQUENCE++;
    }

}
