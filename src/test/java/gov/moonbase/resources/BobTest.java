package gov.moonbase.resources;

import gov.moonbase.model.User;
import gov.moonbase.services.concrete.BobService;
import gov.moonbase.services.iface.BobServiceTemplate;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class BobTest {

    BobServiceTemplate service;

 //   @Test
 //   void testGetPropertiesFromFileName() {
 //       Bob bob = new Bob();
 //   }

    @Test
    public void testBobGet() throws Exception {
        User user = new User("Jason");

        service = mock(BobServiceTemplate.class);
        when(service.existingUsers("Jason")).thenReturn(Arrays.asList(user));

        assertEquals(service.existingUsers("Jason").get(0).getId(), new User("Jason").getId());
    }

}
