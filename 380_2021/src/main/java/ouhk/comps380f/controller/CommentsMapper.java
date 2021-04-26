
package ouhk.comps380f.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ouhk.comps380f.model.Comments;



public class CommentsMapper implements RowMapper<Comments> {
   public Comments mapRow(ResultSet result, int rowNum) throws SQLException {
        Comments comment = new Comments();
                comment.setId(result.getLong("foodid"));
                comment.setFloor(result.getLong("floor"));
                comment.setUsername(result.getString("username"));
                comment.setBody(result.getString("body"));
                
                return comment;
}
}