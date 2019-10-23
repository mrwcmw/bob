package gov.moonbase.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class PropertiesUtil {

    private static PropertiesUtil SINGLETON = null;
    private String fileListPropFileName = "propFileList.properties";

    public static String DB_CONNECTION_STRING;
    public static String DB_USER;
    public static String DB_PASS;

    private PropertiesUtil() {
        reload();
        SINGLETON = this;
    }

    public static PropertiesUtil getInstance(boolean forceReload) {
        if (SINGLETON == null) {
            SINGLETON = new PropertiesUtil();
        }
        else if (forceReload) {
            System.out.println("Call PropertiesUtil.reload");
            SINGLETON.reload();
        }

        return SINGLETON;
    }

    public static PropertiesUtil getInstance() {
        return getInstance(false);
    }

    public static Properties getPropertiesFromFileName (String filename) {
        Properties x = null;
        InputStream stream = null;

        try {
            try {
                x = new Properties();
                stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
                if (stream == null) {
                    stream = new FileInputStream(filename);
                }
                x.load(stream);
            } catch (Exception e) {
                System.out.println("Error Loading properties file");
            } finally {
                stream.close();
            }
        }
        catch (Exception e) {}

        return x;
    }

    private void updateProperties(Properties props) {
        DB_CONNECTION_STRING = props.getProperty("connString");
        DB_USER = props.getProperty("dbUser");
        DB_PASS = props.getProperty("dbPass");
    }

    private List<String> getPropFileList() {
        List<String> filenameList = new ArrayList<>();

        try {
            Properties fileProperties = PropertiesUtil.getPropertiesFromFileName(fileListPropFileName);
            String filenameString = fileProperties.getProperty("filename");
            filenameList = Arrays.asList( filenameString.split(";") );
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return filenameList;
    }

    public void reload() {
        Properties props=new Properties();
        try {
            List<String> filenameList = getPropFileList();

            filenameList.forEach( filename -> {
                props.putAll( PropertiesUtil.getPropertiesFromFileName( filename ) );
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        updateProperties(props);
        System.out.println("PropertiesUtil.reload completed");
    }

}
