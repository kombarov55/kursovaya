package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by nikolaykombarov on 22.03.17.
 */
@Component
public class HibernateUtil {

    static SessionFactory sf = new Configuration().configure().buildSessionFactory();
    private HibernateUtil() {}
    public static Session openSession() {
        return sf.openSession();
    }



}
