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
import ouhk.comps380f.model.Shophist;



public class ShophistMapper implements RowMapper<Shophist> {

    public Shophist mapRow(ResultSet result, int rowNum) throws SQLException {
        Shophist shophist = new Shophist();
        shophist.setCartid(result.getLong("cartid"));
        shophist.setUserid(result.getLong("userid"));
        shophist.setUsername(result.getString("username"));
        shophist.setFoodid(result.getLong("foodid"));
        shophist.setFoodname(result.getString("foodname"));
        shophist.setNoffood(result.getLong("noffood"));
        shophist.setPrice(result.getDouble("price"));
        shophist.setOrderdate(result.getString("orderdate"));

        
        return shophist;
    }
}
