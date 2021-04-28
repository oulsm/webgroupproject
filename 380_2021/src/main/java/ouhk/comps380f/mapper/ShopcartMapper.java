/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ouhk.comps380f.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ouhk.comps380f.model.Shopcart;
import ouhk.comps380f.model.Shopcart;

/**
 *
 * @author Timlui
 */
public class ShopcartMapper implements RowMapper<Shopcart> {

    public Shopcart mapRow(ResultSet result, int rowNum) throws SQLException {
        Shopcart shopcart = new Shopcart();
        shopcart.setCartid(result.getLong("cartid"));
        shopcart.setUserid(result.getLong("userid"));
        shopcart.setUsername(result.getString("username"));
        shopcart.setFoodid(result.getLong("foodid"));
        shopcart.setFoodname(result.getString("foodname"));
        shopcart.setNoffood(result.getLong("noffood"));
        shopcart.setPrice(result.getDouble("price"));
        return shopcart;
    }
}
