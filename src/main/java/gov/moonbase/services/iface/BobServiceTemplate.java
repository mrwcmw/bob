package gov.moonbase.services.iface;

import gov.moonbase.model.Patch;
import gov.moonbase.model.User;

import java.util.List;

public interface BobServiceTemplate {
    public List<User> newUser(User user, String id) throws Exception;
    public List<User> existingUsers(String id) throws Exception;
    public int updateUser(String id, String actorDn, Patch patch) throws Exception;
}
