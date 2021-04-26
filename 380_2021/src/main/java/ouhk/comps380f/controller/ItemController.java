package ouhk.comps380f.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.Comments;
import ouhk.comps380f.model.Item;
import sun.security.krb5.internal.Ticket;

@Controller
@RequestMapping("/food")
public class ItemController {

    private volatile long ITEM_ID_SEQUENCE = 1;
    private Map<Long, Item> itemDatabase = new Hashtable<>();
  

    @GetMapping(value = {"", "/item"})
    public String list(ModelMap model) {
        DriverManagerDataSource ds;
        ds = new DriverManagerDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        ds.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
        ds.setUrl("jdbc:derby://localhost:1527/food");
        ds.setUsername("nbuser");
        ds.setPassword("nbuser");
        // Item item = new Item();
        String sqlSelect = "SELECT * FROM FOODLIST ORDER BY FOODID";
        List<Item> Itemlist = jdbcTemplate.query(sqlSelect, new ItemMapper());
        for (Item lists : Itemlist) {
            System.out.println("id = " + lists.getId());
            System.out.println("foodname" + lists.getFoodname());
            this.itemDatabase.put(lists.getId(), lists);
        }
        System.out.println("map" + itemDatabase);
        //this.itemDatabase.put(lists.getId(), lists);

        // jdbcTemplate.queryForObject("SELECT * FROM FOODLIST","",itemDatabase );
        model.addAttribute("itemDatabase", itemDatabase);
        return "list";
    }

    @PostMapping(value = {"/login","/item/view/login"})
    public ModelAndView login() {
        return new ModelAndView(new RedirectView("/login", true));
    }

    //  private volatile long ITEM_ID_SEQUENCE = 1;
    // private Map<Long, Ticket> ticketDatabase = new Hashtable<>();
    @GetMapping("/create")
    public ModelAndView create() {

        return new ModelAndView("add", "itemForm", new Form());
    }

    public static class Form {

        private String foodname;
        private String description;
        private String price;
        private String noffood;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNoffood() {
            return noffood;
        }

        public void setNoffood(String noffood) {
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

    private String username;
    private String body;


 

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

       
    }

    @PostMapping("/create")
    public View create(Form form, Principal principal) throws IOException {
        Item item = new Item();
        // item.setId(this.getNextItemId());
        //item.setCustomerName(principal.getName());
        //item.setSubject(form.getSubject());
        //item.setBody(form.getBody());
        //item.setFoodname(form.getFoodname());
        //item.setDescription(form.getDescription());
        //item.setPrice(form.getPrice());
        //  item.setNoffood(form.getNoffood());

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
        DriverManagerDataSource ds;
        ds = new DriverManagerDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        ds.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
        ds.setUrl("jdbc:derby://localhost:1527/food");
        ds.setUsername("nbuser");
        ds.setPassword("nbuser");
        jdbcTemplate.update(
                "INSERT INTO FOODLIST(foodname, description, price, noffood) VALUES (?, ?, ?, ?)", form.getFoodname(), form.getDescription(), form.getPrice(), form.getNoffood());

        // this.itemDatabase.put(item.getId(), item);
        //return new RedirectView("/item/view/" + item.getId(), true);
        return new RedirectView("/food/item");

    }

    @GetMapping("/item/view/{itemId}")
    public ModelAndView view(@PathVariable("itemId") long itemId,
            ModelMap model) {
        Map<Long, Comments> commentDatabase = new Hashtable<>();
        Item item = this.itemDatabase.get(itemId);
        if (item == null) {
            
            return new ModelAndView(new RedirectView("/food/list", true));
        }
        
        
        DriverManagerDataSource ds;
        ds = new DriverManagerDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        ds.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
        ds.setUrl("jdbc:derby://localhost:1527/food");
        ds.setUsername("nbuser");
        ds.setPassword("nbuser");
        String sqlSelect = "SELECT * FROM COMMENTS WHERE FOODID = "+itemId;
        List<Comments> Commentslist = jdbcTemplate.query(sqlSelect,new CommentsMapper());
        for (Comments comment : Commentslist) {
            System.out.println("id = " + comment.getId());
             System.out.println("floor = " + comment.getFloor());
            System.out.println("username" + comment.getUsername());
             System.out.println("body" + comment.getBody());
            commentDatabase.put(comment.getFloor(), comment);
               for (Object key : commentDatabase.keySet()) {
            System.out.println(key + " : " + commentDatabase.get(key));
        }
        }
      
     
        // System.out.println(commentDatabase);
        model.addAttribute("commentDatabase",commentDatabase );
        
        model.addAttribute("itemId", itemId);
        model.addAttribute("item", item);
        return new ModelAndView("view", "CForm", new CForm());
        
    }
 @PostMapping("/item/view/{itemId}")
    public ModelAndView viewinsert(@PathVariable("itemId") long itemId,CForm form, Principal principal,
            ModelMap model) {
         Map<Long, Comments> commentDatabase = new Hashtable<>();
        Item item = this.itemDatabase.get(itemId);
        if (item == null) {
            
            return new ModelAndView(new RedirectView("/food/list", true));
        }
        DriverManagerDataSource ds;
        ds = new DriverManagerDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        ds.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
        ds.setUrl("jdbc:derby://localhost:1527/food");
        ds.setUsername("nbuser");
        ds.setPassword("nbuser");
        jdbcTemplate.update(
          "INSERT INTO COMMENTS(foodid, username, body) VALUES (?, ?, ?)", itemId ,principal.getName(), form.getBody());
         String sqlSelect = "SELECT * FROM COMMENTS WHERE FOODID = "+itemId;
        List<Comments> Commentslist = jdbcTemplate.query(sqlSelect,new CommentsMapper());
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
        model.addAttribute("commentDatabase",commentDatabase );
        model.addAttribute("itemId", itemId);
        model.addAttribute("item", item);
        return new ModelAndView("view", "CForm", new CForm());
        
    }
    private synchronized long getNextItemId() {
        return this.ITEM_ID_SEQUENCE++;
    }

}
