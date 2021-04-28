/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ouhk.comps380f.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ouhk.comps380f.model.Comments;
import ouhk.comps380f.model.Register;

/**
 *
 * @author Timlui
 */
public class RegisterMapper implements RowMapper<Register> {

    public Register mapRow(ResultSet result, int rowNum) throws SQLException {
        Register register = new Register();
       
        register.setId(result.getLong("userid"));
        register.setUsername(result.getString("username"));
            register.setPassword(result.getString("password"));
            register.setFullname(result.getString("fullname"));
            register.setPhonenumber(result.getLong("phonenumber"));
            register.setDelivery_address(result.getString("delivery_address"));
            return register;
    }
}
