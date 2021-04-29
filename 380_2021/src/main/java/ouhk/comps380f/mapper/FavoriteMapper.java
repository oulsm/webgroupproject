/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ouhk.comps380f.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ouhk.comps380f.model.Favorite;

public class FavoriteMapper implements RowMapper<Favorite> {

    public Favorite mapRow(ResultSet result, int rowNum) throws SQLException {
        Favorite favorite = new Favorite();
        favorite.setCartid(result.getLong("cartid"));
        favorite.setUserid(result.getLong("userid"));
        favorite.setUsername(result.getString("username"));
        favorite.setFoodid(result.getLong("foodid"));
        favorite.setFoodname(result.getString("foodname"));
        favorite.setPrice(result.getDouble("price"));
        return favorite;
    }
}