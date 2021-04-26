/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ouhk.comps380f.model;

/**
 *
 * @author Timlui
 */
public class Comments {
    private long id;
    private long floor;
    private String username;
    private String body;

    public long getFloor() {
        return floor;
    }

    public void setFloor(long floor) {
        this.floor = floor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    

 
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
}
