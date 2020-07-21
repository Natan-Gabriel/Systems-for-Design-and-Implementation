package repository.repos;

import domain.Entities.Movie;
import javafx.util.Pair;
import domain.Entities.Rental;
import domain.Validators.Validator;
import domain.Validators.ValidatorException;
import org.springframework.jdbc.core.JdbcOperations;
import repository.impl.Sort;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class DBRentalRepository implements SortingRepository<Long, Rental> {

    private Map<Long, Rental> entities;
    private Validator<Rental> validator;

    private JdbcOperations jdbcOperations;

    public DBRentalRepository(Validator<Rental> validator, JdbcOperations jdbcOperations) {
        this.validator = validator;
        this.jdbcOperations=jdbcOperations;
    }


    @Override
    public Iterable<Rental> findAll(Sort sort) throws SQLException {
        String sql = "select * from rentals";
        entities=jdbcOperations.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String serial = rs.getString("serial");
            Long mid = rs.getLong("movieid");
            Long cid = rs.getLong("clientid");
            boolean returned = rs.getBoolean("returned");

            Rental ren = new Rental(serial, mid, cid, returned);
            ren.setId(id);
            return ren;
        }).stream().collect(Collectors.toMap(x->x.getId(), x->x));
        List<Pair<String, Boolean>> criteria = sort.getParams();
        Comparator<Rental> comp = new Comparator<Rental>() {
            @Override
            public int compare(Rental o1, Rental o2){
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
        List<Rental> allEntities = entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
        Collections.sort(allEntities, comp);
        return allEntities;
    }

    @Override
    public Optional<Rental> findOne(Long aLong) throws IOException, ClassNotFoundException, SQLException {
        if (aLong == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        String sql = "select * from rentals where id=?";
        Rental r = jdbcOperations.queryForObject(sql, new Object[]{aLong}, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String serial = rs.getString("serial");
            Long mid = rs.getLong("movieid");
            Long cid = rs.getLong("clientid");
            boolean returned = rs.getBoolean("returned");

            Rental ren = new Rental(serial, mid, cid, returned);
            ren.setId(id);
            return ren;
        });
        Optional<Rental> o = Optional.ofNullable(r);
        return o;
    }

    @Override
    public Iterable<Rental> findAll() throws IOException, ClassNotFoundException, SQLException {
        String sql = "select * from rentals";
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        entities=jdbcOperations.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String serial = rs.getString("serial");
            Long mid = rs.getLong("movieid");
            Long cid = rs.getLong("clientid");
            boolean returned = rs.getBoolean("returned");

            Rental ren = new Rental(serial, mid, cid, returned);
            ren.setId(id);
            return ren;
        }).stream().collect(Collectors.toMap(x->x.getId(), x->x));
        Set<Rental> allEntities = entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
        return allEntities;
    }

    @Override
    public void setAll(Map<Long, Rental> m) throws ClassNotFoundException, ParserConfigurationException, TransformerException, IOException, SQLException {
        entities = m;
        String sql = "delete from rentals";
        jdbcOperations.update(sql);
        for (Rental entity : entities.values()) {
            String sql2 = "insert into rentals (id, serial, movieid, clientid, returned) values (?, ?, ?, ?, ?)";
            jdbcOperations.update(sql2, entity.getId(), entity.getRentalSerial(), entity.getMovieID(), entity.getClientID(), entity.isReturned());
        }
    }

    @Override
    public Optional<Rental> save(Rental entity) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        validator.validate(entity);
        String sql2 = "insert into rentals (id, serial, movieid, clientid, returned) values (?, ?, ?, ?, ?)";
        jdbcOperations.update(sql2, entity.getId(), entity.getRentalSerial(), entity.getMovieID(), entity.getClientID(), entity.isReturned());
        Optional<Rental> o = Optional.ofNullable(entity);
        return o;
    }

    @Override
    public Optional<Rental> delete(Long aLong) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        if (aLong == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        Optional<Rental> o = this.findOne(aLong);
        String sql = "delete from rentals where id=?";
        jdbcOperations.update(sql, aLong);
        return o;
    }

    @Override
    public Optional<Rental> update(Rental entity) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        validator.validate(entity);
        String sql = "update rentals set serial=?, movieid=?, clientid=?, returned=? where id=?";
        jdbcOperations.update(sql, entity.getRentalSerial(), entity.getMovieID(), entity.getClientID(), entity.isReturned(), entity.getId());
        Optional<Rental> o = Optional.ofNullable(entity);
        return o;
    }

    @Override
    public Map<Long, Rental> getAll() throws IOException, ClassNotFoundException, SQLException {
        String sql = "select * from rentals";
        entities=jdbcOperations.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String serial = rs.getString("serial");
            Long mid = rs.getLong("movieid");
            Long cid = rs.getLong("clientid");
            boolean returned = rs.getBoolean("returned");

            Rental ren = new Rental(serial, mid, cid, returned);
            ren.setId(id);
            return ren;
        }).stream().collect(Collectors.toMap(x->x.getId(), x->x));
        return entities;
    }
}
