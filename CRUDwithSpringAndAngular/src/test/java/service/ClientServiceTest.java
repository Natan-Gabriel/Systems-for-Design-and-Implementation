package service;

import domain.Client;
import domain.Validators.ClientValidator;
import domain.Validators.Validator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientServiceTest {
    private ClientService service;
    private Validator<Client> clientValidator;
    private InMemoryRepository repository;
    private String name="ana";
    private int age=10;
    private long ID=1;
    private String serialNumber="12";
    @Before
    public void setUp() throws Exception {
        clientValidator = new ClientValidator();
        repository = new InMemoryRepository(clientValidator);
        service = new ClientService(repository);
    }

    @After
    public void tearDown() throws Exception {
        clientValidator=null;
        repository=null;
        service=null;
    }

    @Test
    public void testAddClient() throws Exception
    {
        Client c=new Client(name,age,serialNumber);
        c.setId(ID);
        service.addClient(c);
        assertEquals("should be equal", c, service.getAllClients().toArray()[0]);
    }

    @Test
    public void testGetAllClients() throws Exception{
        Client c=new Client(name,age,serialNumber);
        c.setId(ID);
        Client d=new Client("Dan",22, "120");
        d.setId((long) 120);
        service.addClient(c);
        service.addClient(d);
        assertEquals("should be equal", c, service.getAllClients().toArray()[0]);
        assertEquals("should be equal", d, service.getAllClients().toArray()[1]);
    }

    @Test
    public void testFilterClientsBySerial() throws Exception{
        Client c=new Client(name,age,serialNumber);
        c.setId(ID);
        Client d=new Client("Dan",22, "120");
        d.setId((long) 120);
        service.addClient(c);
        service.addClient(d);
        assertEquals("should be equal", c, service.filterClientsBySerial(serialNumber));
    }
}
