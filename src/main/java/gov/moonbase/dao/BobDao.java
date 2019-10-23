package gov.moonbase.dao;

import gov.moonbase.model.Patch;
import gov.moonbase.model.User;
import gov.moonbase.util.PropertiesUtil;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BobDao {
    private static BobDao SINGLETON = null;
    private static final Function<String, String> PATCH_STATEMENT = columnName -> "UPDATE users SET " + columnName + " = ? WHERE dn = ?";

    public static BobDao getInstance() {
        if (SINGLETON == null) {
            SINGLETON = new BobDao();
        }

        return SINGLETON;
    }

    public int addNewUser(String id) throws SQLException {
        QueryRunner run = new QueryRunner();
        Connection conn = getConnection();
        int results = 0;

        try {
            results = run.update(
                    conn,
                    "INSERT INTO users (dn)" +
                            " VALUES (?)",
                    id
            );
        }
        catch (SQLException e) {
            System.out.println("BobDao.addNewUser(String) - Unable to insert into users");
            throw e;
        }
        finally {
            DbUtils.close(conn);
        }

        return results;
    }

    public int addNewUser(User user) throws SQLException {
        QueryRunner run = new QueryRunner();
        Connection conn = getConnection();
        int results = 0;

        try {
            results = run.update(
                    conn,
                    "INSERT INTO users (dn, last_name, first_name, organization, role)" +
                            " VALUES (?,?,?,?,?)",
                    user.getId(),
                    user.getLastName(),
                    user.getFirstName(),
                    user.getOrganization(),
                    user.getRole()
            );
        }
        catch (Exception e) {
            System.out.println("BobDao.addNewUser(User) - Unable to insert into users");
            throw e;
        }
        finally {
            DbUtils.close(conn);
        }

        return results;
    }

    public List<User> getExistingUser(String id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        Connection conn = getConnection();
        List<User> userList = new ArrayList<>();

        try {
            userList = (List<User>) queryRunner.query(
                 conn,
                 "SELECT dn as id, first_name AS firstName, last_name AS lastName, organization, role " +
                         " FROM users " +
                         " WHERE dn = ?",
                 new BeanListHandler(User.class),
                 id
            );
        }
        catch(Exception e){
            System.out.println("BobDao.getExistingUser(String) - Unable to select from users");
            throw e;
        }
        finally {
            DbUtils.close(conn);
        }

        return userList;
    }

    public int updateUser (String id, Patch patch) throws SQLException {
        QueryRunner run = new QueryRunner();
        Connection conn = getConnection();
        int updates = 0;

        try {
            updates = run.update(
                    conn,
                    PATCH_STATEMENT.apply(patch.getPath()),
                    patch.getValue(),
                    id
            );
        }
        catch (SQLException e) {
            System.out.println("BobDao.updateUser(String, Patch) - Unable to update user");
            throw e;
        }
        finally {
            DbUtils.close(conn);
        }

        return updates;
    }

    private Connection getConnection() {
        DbUtils.loadDriver("com.mysql.cj.jdbc.Driver");
        PropertiesUtil props = PropertiesUtil.getInstance();
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(
                    props.DB_CONNECTION_STRING, props.DB_USER, props.DB_PASS
            );
        }
        catch (SQLException sqle) {
            System.out.println("Unable to create connection.  Stack: " + sqle);
        }

        return conn;
    }
}
