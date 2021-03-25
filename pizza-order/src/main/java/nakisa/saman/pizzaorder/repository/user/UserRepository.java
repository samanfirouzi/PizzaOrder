package nakisa.saman.pizzaorder.repository.user;

import nakisa.saman.pizzaorder.model.entry.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserData getUserDetails(String username) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String userSQLQuery = "SELECT * FROM USER_DATA WHERE USERNAME=?";

        List<UserData> list = jdbcTemplate.query(userSQLQuery,
                (ResultSet rs, int rowNum) -> {
                    UserData user = new UserData();
                    user.setId(rs.getLong("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setFullName(rs.getString("full_name"));
                    user.setUgrant(rs.getString("ugrant"));
                    return user;
                }, username);
        if (list.size() > 0) {
            if(list.size()>1) return null;//it's just will happened when database field is not unique.
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(list.get(0).getUgrant());
            grantedAuthorities.add(grantedAuthority);
            list.get(0).setGrantedAuthorities(grantedAuthorities);
            return list.get(0);
        }
        return null;
    }
}
