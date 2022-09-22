//package template.callback.JdbcTemplate;
//
///**
// * @author fangxu
// * on date:2022/9/22
// */
//
//public class JdbcTemplateDemo {
//    private JdbcTemplate jdbcTemplate;
//
//    public User queryUser(long id) {
//        String sql = "select * from user where id="+id;
//        return jdbcTemplate.query(sql, new UserRowMapper()).get(0);
//    }
//
//    class UserRowMapper implements RowMapper<User> {
//        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//            User user = new User();
//            user.setId(rs.getLong("id"));
//            user.setName(rs.getString("name"));
//            user.setTelephone(rs.getString("telephone"));
//            return user;
//        }
//    }
//}
