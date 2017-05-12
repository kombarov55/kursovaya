package db;


import dto.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class UserRoleDAO extends HibernateDaoSupport {

    @Autowired DataSource ds;

    @Transactional public void save(UserRole userRole) {
        getHibernateTemplate().save(userRole);
    }

    @Transactional public UserRole getById(long roleId) {
        String sql = "select role from userrole where id = ?";
        UserRole ret = null;
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, roleId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String roleName = rs.getString(1);
            ret = new UserRole(roleId, roleName);
            ret = new UserRole();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return ret;
    }

    public UserRole getByName(String roleName) {
        UserRole userRole = findInDb(roleName);
        if (userRole == null)
            return createSaveAndReturn(roleName);
        else
            return userRole;
    }

    private UserRole findInDb(String roleName) {
        String sql = "select role from userrole where role like ?";
        UserRole ret = null;
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, roleName);
            ResultSet rs = ps.executeQuery();
            rs.next();
            long roleId = rs.getLong(1);
            ret = new UserRole(roleId, roleName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return ret;
    }

    private UserRole createSaveAndReturn(String roleName) {
        UserRole userRole = new UserRole(1, roleName);
        save(userRole);
        return userRole;
    }
}
