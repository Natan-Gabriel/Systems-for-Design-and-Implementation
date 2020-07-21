package repository;

import domain.Client;
import domain.Validators.Validator;
import javafx.util.Pair;
import domain.Client;
import domain.Validators.Validator;
import domain.Validators.ValidatorException;
import repository.impl.Sort;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class DBClientRepository implements SortingRepository<Long, Client> {

    private Map<Long, Client> entities;
    private Validator<Client> validator;

    private static final String URL = "jdbc:postgresql://localhost:1234/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "terno";

    public DBClientRepository(Validator<Client> validator) {
        this.validator = validator;
    }



    private Map<Long, Client> readFile() throws SQLException {
        Map<Long, Client> map = new HashMap<>();
        String sql = "select * from clients";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next())
        {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String serial = resultSet.getString("serial");
            int age = resultSet.getInt("age");

            Client cl = new Client(name, age, serial);
            cl.setId(id);

            map.put(id, cl);
        }
        return map;
    }

    private void writeFile() throws SQLException, ClassNotFoundException, ParserConfigurationException, TransformerException, IOException {
        String sql = "delete from clients";
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        for (Client entity:entities.values()
        ) {
            String sql2 = "insert into clients (id, name, serial, age) values (?, ?, ?, ?)";
            Connection connection2 = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement2 = connection2.prepareStatement(sql2);
            preparedStatement2.setLong(1, entity.getId());
            preparedStatement2.setString(2, entity.getName());
            preparedStatement2.setString(3, entity.getSerial());
            preparedStatement2.setInt(4, entity.getAge());
            preparedStatement2.executeUpdate();
        }
    }

    @Override
    public Iterable<Client> findAll(Sort sort) throws SQLException {
        entities=readFile();
        List<Pair<String, Boolean>> criteria = sort.getParams();
        Comparator<Client> comp = new Comparator<Client>() {
            @Override
            public int compare(Client o1, Client o2){
                for (Pair<String, Boolean> p:criteria) {
                    try {
                        Field f = o1.getClass().getDeclaredField(p.getKey());
                        f.setAccessible(true);
                        Comparable<Object> obj1 = (Comparable<Object>) f.get(o1);
                        Comparable<Object> obj2 = (Comparable<Object>) f.get(o2);
                        if(obj1.compareTo(obj2)!=0)
                            if(p.getValue())
                                return obj1.compareTo(obj2);
                            else
                                return obj2.compareTo(obj1);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
                return 0;
            }
        };
        List<Client> allEntities = entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
        Collections.sort(allEntities, comp);
        return allEntities;
    }

    @Override
    public Optional<Client> findOne(Long aLong) throws IOException, ClassNotFoundException, SQLException {
        if (aLong == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        entities = readFile();
        return Optional.ofNullable(entities.get(aLong));
    }

    @Override
    public Iterable<Client> findAll() throws IOException, ClassNotFoundException, SQLException {
        entities=readFile();
        Set<Client> allEntities = entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
        return allEntities;
    }

    @Override
    public void setAll(Map<Long, Client> m) throws ClassNotFoundException, ParserConfigurationException, TransformerException, IOException, SQLException {
        entities=m;
        this.writeFile();
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        validator.validate(entity);
        entities = readFile();
        Optional<Client> optional =  Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
        writeFile();
        return optional;
    }

    @Override
    public Optional<Client> delete(Long aLong) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        if (aLong == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        entities = readFile();
        Optional<Client>  opt = Optional.ofNullable(entities.remove(aLong));
        writeFile();
        return opt;
    }

    @Override
    public Optional<Client> update(Client entity) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        validator.validate(entity);
        entities = readFile();
        Optional<Client> r = Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
        writeFile();
        return r;
    }

    @Override
    public Map<Long, Client> getAll() throws IOException, ClassNotFoundException, SQLException {
        entities=readFile();
        return entities;
    }
}
