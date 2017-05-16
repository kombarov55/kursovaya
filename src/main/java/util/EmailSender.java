package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import db.CategoryDAO;
import db.ClientDAO;
import db.ItemDAO;
import db.ShopDAO;
import dto.Item;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmailSender {
    String username, password, receivingHost;
    ObjectMapper om = new ObjectMapper();

    @Autowired ItemDAO itemDAO;
    @Autowired CategoryDAO categoryDAO;
    @Autowired ShopDAO shopDAO;
    @Autowired ClientDAO clientDAO;

    public EmailSender(String username, String password) {
        this.username = username;
        this.password = password;
        startDaemon();
    }

    private void startDaemon() {
        Thread daemon = new Thread(() -> {
            while (true) {
                try {
                    readGmail();
                    Thread.sleep(1000 * 60 * 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        daemon.setDaemon(true);
        daemon.start();
    }


    private void readGmail() {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session2 = Session.getDefaultInstance(props, null);

        try {

            Store store = session2.getStore("imaps");

            store.connect(receivingHost, username, password);

            Folder folder = store.getFolder("INBOX");

            folder.open(Folder.READ_ONLY);

            Message messages[] = folder.getMessages();

            List<String> filteredMessages = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                if (messages[i].getSubject().equals("kursovaya"))
                    filteredMessages.add(messages[i].getSubject());
            }

            save(filteredMessages);

            folder.close(true);
            store.close();

        } catch (Exception e) {

            System.out.println(e.toString());

        }

    }

    private void save(List<String> messages) throws Exception {
        List<Item> items = parseFromJson(messages);
        for (Item each : items) {
            substituteShop(each);
        }
        itemDAO.saveAll(items);

    }

    private void substituteShop(Item each) {
        each.seller = shopDAO.getByName(each.seller.name);
    }

    private List<Item> parseFromJson(List<String> messages) throws Exception {
        List<Item> ret = new ArrayList<>();
        for (String each : messages) {
            ret.add(om.readValue(each, Item.class));
        }
        return ret;
    }

}
