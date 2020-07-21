package repository.repos;

import domain.Entities.Client;
import domain.Entities.Entity;
import domain.Validators.Validator;
import javafx.util.Pair;
import domain.Validators.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
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

    private JdbcOperations jdbcOperations;


    public DBClientRepository(Validator<Client> validator, JdbcOperations jdbcOperations) {
        this.validator = validator;
        this.jdbcOperations=jdbcOperations;
    }


    @Override
    public Iterable<Client> findAll(Sort sort) throws SQLException {
        String sql = "select * from clients";
        entities=jdbcOperations.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String serial = rs.getString("serial");
            int age = rs.getInt("age");
            Client c = new Client(name, age, serial);
            c.setId(id);
            return c;
        }).stream().collect(Collectors.toMap(x->x.getId(), x->x));
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
        String sql = "select * from clients where id=?";
        Client c = jdbcOperations.queryForObject(sql, new Object[]{aLong}, (rs, rowNum) -> {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String serial = rs.getString("serial");
                int age = rs.getInt("age");
                Client cl = new Client(name, age, serial);
                cl.setId(id);
                return cl;
        });
        Optional<Client> o = Optional.ofNullable(c);
        return o;
    }

    @Override
    public Iterable<Client> findAll() throws IOException, ClassNotFoundException, SQLException {
        String sql = "select * from clients";
        entities=jdbcOperations.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String serial = rs.getString("serial");
            int age = rs.getInt("age");
            Client c = new Client(name, age, serial);
            c.setId(id);
            return c;
        }).stream().collect(Collectors.toMap(x->x.getId(), x->x));
        Set<Client> allEntities = entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
        return allEntities;
    }

    @Override
    public void setAll(Map<Long, Client> m) throws ClassNotFoundException, ParserConfigurationException, TransformerException, IOException, SQLException {
        entities = m;
        String sql = "delete from clients";
        jdbcOperations.update(sql);
        for (Client entity : entities.values()) {
            String sql2 = "insert into clients (id, name, serial, age) values (?, ?, ?, ?)";
            jdbcOperations.update(sql2, entity.getId(), entity.getName(), entity.getSerial(), entity.getAge());
        }
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        validator.validate(entity);
        String sql = "insert into clients (id, name, serial, age) values (?, ?, ?, ?)";
        jdbcOperations.update(sql, entity.getId(), entity.getName(), entity.getSerial(), entity.getAge());
        Optional<Client> o = Optional.ofNullable(entity);
        return o;
    }

    @Override
    public Optional<Client> delete(Long aLong) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        if (aLong == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        Optional<Client> o = this.findOne(aLong);
        String sql = "delete from clients where id=?";
        jdbcOperations.update(sql, aLong);
        return o;
    }

    @Override
    public Optional<Client> update(Client entity) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        validator.validate(entity);
        String sql = "update clients set name=?, serial=?, age=? where id=?";
        jdbcOperations.update(sql, entity.getName(), entity.getSerial(), entity.getAge(), entity.getId());
        Optional<Client> o = Optional.ofNullable(entity);
        return o;
    }

    @Override
    public Map<Long, Client> getAll() throws IOException, ClassNotFoundException, SQLException {
        String sql = "select * from clients";
        entities=jdbcOperations.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String serial = rs.getString("serial");
            int age = rs.getInt("age");
            Client c = new Client(name, age, serial);
            c.setId(id);
            return c;
        }).stream().collect(Collectors.toMap(x->x.getId(), x->x));
        return entities;
    }
}
