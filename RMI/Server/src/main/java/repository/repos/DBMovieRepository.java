package repository.repos;

import javafx.util.Pair;
import domain.Entities.Movie;
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

public class DBMovieRepository implements SortingRepository<Long, Movie> {
    private Map<Long, Movie> entities;
    private Validator<Movie> validator;

    private JdbcOperations jdbcOperations;

    public DBMovieRepository(Validator<Movie> validator, JdbcOperations jdbcOperations) {
        this.validator = validator;
        this.jdbcOperations=jdbcOperations;
    }


    @Override
    public Iterable<Movie> findAll(Sort sort) throws Exception {
        String sql = "select * from movies";
        entities=jdbcOperations.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("title");
            String serial = rs.getString("serial");
            int year = rs.getInt("year");
            int rating = rs.getInt("rating");
            Movie m = new Movie(name, year, rating, serial);
            m.setId(id);
            return m;
        }).stream().collect(Collectors.toMap(x->x.getId(), x->x));
        List<Pair<String, Boolean>> criteria = sort.getParams();
        Comparator<Movie> comp = new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2){
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
        List<Movie> allEntities = entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
        Collections.sort(allEntities, comp);
        return allEntities;
    }

    @Override
    public Optional<Movie> findOne(Long aLong) throws IOException, ClassNotFoundException, SQLException {
        if (aLong == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        String sql = "select * from movies where id=?";
        Movie m = jdbcOperations.queryForObject(sql, new Object[]{aLong}, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("title");
            String serial = rs.getString("serial");
            int year = rs.getInt("year");
            int rating = rs.getInt("rating");
            Movie mo = new Movie(name, year, rating, serial);
            mo.setId(id);
            return mo;
        });
        Optional<Movie> o = Optional.ofNullable(m);
        return o;
    }

    @Override
    public Iterable<Movie> findAll() throws IOException, ClassNotFoundException, SQLException {
        String sql = "select * from movies";
        entities=jdbcOperations.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("title");
            String serial = rs.getString("serial");
            int year = rs.getInt("year");
            int rating = rs.getInt("rating");
            Movie m = new Movie(name, year, rating, serial);
            m.setId(id);
            return m;
        }).stream().collect(Collectors.toMap(x->x.getId(), x->x));
        Set<Movie> allEntities = entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
        return allEntities;
    }

    @Override
    public void setAll(Map<Long, Movie> m) throws ClassNotFoundException, ParserConfigurationException, TransformerException, IOException, SQLException {
        entities = m;
        String sql = "delete from movies";
        jdbcOperations.update(sql);
        for (Movie entity : entities.values()) {
            String sql2 = "insert into movies (id, title, year, rating, serial) values (?, ?, ?, ?, ?)";
            jdbcOperations.update(sql2, entity.getId(), entity.getName(), entity.getYear(), entity.getRating(), entity.getSerial());
        }
    }

    @Override
    public Optional<Movie> save(Movie entity) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        validator.validate(entity);
        String sql2 = "insert into movies (id, title, year, rating, serial) values (?, ?, ?, ?, ?)";
        jdbcOperations.update(sql2, entity.getId(), entity.getName(), entity.getYear(), entity.getRating(), entity.getSerial());
        Optional<Movie> o = Optional.ofNullable(entity);
        return o;
    }

    @Override
    public Optional<Movie> delete(Long aLong) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        if (aLong == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        Optional<Movie> o = this.findOne(aLong);
        String sql = "delete from movies where id=?";
        jdbcOperations.update(sql, aLong);
        return o;
    }

    @Override
    public Optional<Movie> update(Movie entity) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        validator.validate(entity);
        String sql = "update movies set title=?, year=?, rating=?, serial=? where id=?";
        jdbcOperations.update(sql, entity.getName(), entity.getYear(), entity.getRating(), entity.getSerial(), entity.getId());
        Optional<Movie> o = Optional.ofNullable(entity);
        return o;
    }

    @Override
    public Map<Long, Movie> getAll() throws IOException, ClassNotFoundException, SQLException {
        String sql = "select * from movies";
        entities=jdbcOperations.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("title");
            String serial = rs.getString("serial");
            int year = rs.getInt("year");
            int rating = rs.getInt("rating");
            Movie m = new Movie(name, year, rating, serial);
            m.setId(id);
            return m;
        }).stream().collect(Collectors.toMap(x->x.getId(), x->x));
        return entities;
    }
}
