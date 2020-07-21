package repository;


import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class InMemoryRepositoryTest {

//    private Validator<Client> clientValidator;
//    private InMemoryRepository repository;
//    private String name="ana";
//    private int age=10;
//    private long ID=1;
//    private String serialNumber="12";
//    @Before
//    public void setUp() throws Exception {
//        clientValidator = new ClientValidator();
//        repository = new InMemoryRepository(clientValidator);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        clientValidator=null;
//        repository=null;
//    }
//
//
//    @Test
//    public void testFindOne() throws Exception {
//        Client c=new Client(name,age,serialNumber);
//        c.setId(ID);
//        repository.save(c);
//        assertEquals("should be equal",c, repository.findOne(ID).get());
//    }
//
//    @Test
//    public void testFindAll() throws Exception {
//        Client c=new Client(name,age,serialNumber);
//        c.setId(ID);
//        repository.save(c);
//        Client e = new Client("Eric", 70, "130");
//        e.setId((long) 200);
//        repository.save(e);
//        Iterator iter = repository.findAll().iterator();
//        assertEquals("should be equal", iter.next(), repository.findOne(e.getId()).get());
//        assertEquals("should be equal",iter.next(), repository.findOne(ID).get());
//    }
//
//    @Test
//    public void testSave() throws Exception {
//        Client c=new Client(name,age,serialNumber);
//        c.setId(ID);
//        repository.save(c);
//        assertEquals("should be equal",c, repository.findOne(ID).get());
//        Client d=new Client("bob", 15, "227");
//        d.setId((long) 100);
//        repository.save(d);
//        assertEquals("should be equal", d, repository.findOne(d.getId()).get());
//    }
//
//    @Test(expected = ValidatorException.class)
//    public void testSaveException() throws Exception {
//        Client c=new Client("",age,serialNumber);
//        c.setId(ID);
//        repository.save(c);
//    }
//
//    @Test
//    public void testDelete() throws Exception {
//        Client c=new Client(name,age,serialNumber);
//        c.setId(ID);
//        repository.save(c);
//        repository.delete(c.getId());
//        assertNotEquals(c, repository.findOne(c.getId()));
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//        Client c=new Client(name,age,serialNumber);
//        c.setId(ID);
//        repository.save(c);
//        c.setAge(99);
//        repository.update(c);
//        assertEquals("should be equal", c, repository.findOne(c.getId()).get());
//    }
//
//    @Test(expected = ValidatorException.class)
//    public void testUpdateException() throws Exception {
//        Client c=new Client(name,age,serialNumber);
//        c.setId(ID);
//        repository.save(c);
//        c.setName("");
//        repository.update(c);
//    }
}
