package gov.moonbase.services.concrete;

import gov.moonbase.dao.BobDao;
import gov.moonbase.model.Patch;
import gov.moonbase.model.User;

import java.util.ArrayList;
import java.util.List;

public class BobService implements gov.moonbase.services.iface.BobServiceTemplate {

    private static BobService SINGLETON = null;

    public static BobService getInstance() {
        if (SINGLETON == null) {
            SINGLETON = new BobService();
        }

        return SINGLETON;
    }

    public List<User> newUser(User user, String id) throws Exception {
        int inserts;
        List<User> userList = new ArrayList<>();
        if ( id != null && !id.isEmpty() && user==null ) {
             inserts = BobDao.getInstance().addNewUser(id);

             if (inserts > 0) {
                 userList = existingUsers(id);
             }
        }
        else if ( user != null && (id == null || id.isEmpty()) ) {
             inserts = BobDao.getInstance().addNewUser(user);

             if (inserts > 0) {
                 userList = existingUsers(user.getId());
             }
        }

        return userList;
    }

    public List<User> existingUsers(String id) throws Exception {
        List<User> userList = new ArrayList<>();
        userList = BobDao.getInstance().getExistingUser(id);

        userList.forEach(user-> {
             System.out.println("userList item id: " + user.getId());
        });

        return userList;
    }

    public int updateUser(String id, String actorDn, Patch patch) throws Exception {
        if (patch.getPath().toLowerCase().equals("lastname"))
            patch.setPath("last_name");
        if (patch.getPath().toLowerCase().equals("firstname"))
            patch.setPath("first_name");

        int updates = BobDao.getInstance().updateUser(id, patch);

        return updates;
    }
}
