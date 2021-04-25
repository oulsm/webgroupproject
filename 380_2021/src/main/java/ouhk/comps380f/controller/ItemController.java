package ouhk.comps380f.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.Item;
import sun.security.krb5.internal.Ticket;


@Controller
@RequestMapping("/food")
public class ItemController {

    private volatile long ITEM_ID_SEQUENCE = 1;
    private Map<Long, Item> itemDatabase = new Hashtable<>();

    @GetMapping(value = {"", "/item"})
    public String list(ModelMap model) {
        model.addAttribute("itemDatabase", itemDatabase);
        return "list";
    }



    @PostMapping("/login")
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

        private long id;
        private String description;
        private String price;
        private String noffood;
        private List<MultipartFile> attachments;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
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

    @PostMapping("/create")
    public View create(Form form, Principal principal) throws IOException {
        Item item = new Item();
        item.setId(this.getNextItemId());
        //item.setCustomerName(principal.getName());
        //item.setSubject(form.getSubject());
        //item.setBody(form.getBody());
        item.setDescription(form.getDescription());
        item.setPrice(form.getPrice());
        item.setNoffood(form.getNoffood());
        
        

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
        this.itemDatabase.put(item.getId(), item);
        return new RedirectView("/item/view/" + item.getId(), true);
        
    }

    private synchronized long getNextItemId() {
        return this.ITEM_ID_SEQUENCE++;
    }

}
