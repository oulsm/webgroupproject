/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ouhk.comps380f.controller;



import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ouhk.comps380f.model.Item;

public class ItemMapper implements RowMapper<Item> {
   public Item mapRow(ResultSet result, int rowNum) throws SQLException {
        Item item = new Item();
                item.setId(result.getLong("foodid"));
                item.setFoodname(result.getString("foodname"));
                item.setDescription(result.getString("description"));
                item.setPrice(result.getString("price"));
                item.setNoffood(result.getString("noffood"));
                return item;
}
}

