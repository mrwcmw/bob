package gov.moonbase;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;

import gov.moonbase.dao.BobDao;
import gov.moonbase.services.concrete.BobService;
import gov.moonbase.util.PropertiesUtil;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Info;

@ApplicationPath(value="services")
public class ApplicationConfig extends Application {

    public ApplicationConfig() {
        Info info = new Info();
        info.setVersion("0.1");
        info.setTitle("Bob Title");
        info.setDescription("Bob Description");

        BeanConfig bConfig = new BeanConfig();
        bConfig.getSwagger().setInfo(info);
        bConfig.setResourcePackage(this.getClass().getPackage().getName());
        bConfig.setBasePath("bob/services");
        bConfig.setHost("localhost:8080");
        bConfig.setSchemes(new String[]{"http"});
        bConfig.setContact("willchm@cia.ic.gov");
        bConfig.setPrettyPrint(true);
        bConfig.setScan(true);
        bConfig.setTitle("Bob Integration Service");

        PropertiesUtil.getInstance();
        BobDao.getInstance();
        BobService.getInstance();
    }

}
