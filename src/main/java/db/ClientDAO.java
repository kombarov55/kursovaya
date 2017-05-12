package db;

import dto.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDAO {

    @Autowired DataSource ds;
    @Autowired UserRoleDAO userRoleDAO;
    @Autowired SessionFactory sessionFactory;

    List<Client> cache = new ArrayList<>();

    public Client findByUsername(String username) {
        String sql = "SELECT id, password, userrole_id FROM client WHERE username LIKE ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            long id = rs.getLong(1);
            String password = rs.getString(2);
            long roleId = rs.getLong(3);
            return new Client(id, username, password, userRoleDAO.getById(roleId));
        } catch (Exception ignored) {
            ignored.printStackTrace();
            System.out.println(ignored.getMessage());
        }
        return null;
    }

    public Client getById(long id) {
        if (id == 0) return null;
        return findFromCache(id).orElse(getFromDbAndSave(id));
    }

    private Optional<Client> findFromCache(long id) {
        return cache.stream().filter(each -> each.id == id).findAny();
    }

    private Client getFromDbAndSave(long id) {
//        String sql = "SELECT username, password, userrole_id FROM client WHERE id = ?";
//        Client ret = null;
//        try (Connection con = ds.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setLong(1, id);
//            ResultSet rs = ps.executeQuery();
//            rs.next();
//            String username = rs.getString(1);
//            String password =
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println(e.toString());
//        }
        try (Session session = sessionFactory.openSession()) {
            Client ret = session.get(Client.class, id);
            cache.add(ret);
            return ret;
        }
    }

    public boolean areCredentialsValid(String username, String password) {
        String sql = "SELECT count (*) FROM client WHERE username LIKE ? AND password LIKE ?";
        boolean userFound = false;
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            rs.next();
            userFound = rs.getInt(1) != 0;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            System.out.println(ignored.getMessage());
        }
        return userFound;
    }

    public void save(Client testClient) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(testClient);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
