
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
                item.setPrice(result.getDouble("price"));
                item.setNoffood(result.getLong("noffood"));
                return item;
}
}

