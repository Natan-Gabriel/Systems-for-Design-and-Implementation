package repository;

import javafx.util.Pair;
import domain.Movie;
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

public class DBMovieRepository implements SortingRepository<Long, Movie> {
    private Map<Long, Movie> entities;
    private Validator<Movie> validator;

    private static final String URL = "jdbc:postgresql://localhost:1234/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "terno";

    public DBMovieRepository(Validator<Movie> validator) {
        this.validator = validator;
    }

    private Map<Long, Movie> readFile() throws SQLException {
        Map<Long, Movie> map = new HashMap<>();
        String sql = "select * from movies";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next())
        {
            Long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            String serial = resultSet.getString("serial");
            int year = resultSet.getInt("year");
            int rating = resultSet.getInt("rating");

            Movie mv = new Movie(title, year, rating, serial);
            mv.setId(id);

            map.put(id, mv);
        }
        return map;
    }

    private void writeFile() throws SQLException, ClassNotFoundException, ParserConfigurationException, TransformerException, IOException {
        String sql = "delete from movies";
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        for (Movie entity:entities.values()
        ) {
            String sql2 = "insert into movies (id, title, year, rating, serial) values (?, ?, ?, ?, ?)";
            Connection connection2 = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement2 = connection2.prepareStatement(sql2);
            preparedStatement2.setLong(1, entity.getId());
            preparedStatement2.setString(2, entity.getName());
            preparedStatement2.setInt(3, entity.getYear());
            preparedStatement2.setInt(4, entity.getRating());
            preparedStatement2.setString(5, entity.getSerial());
            preparedStatement2.executeUpdate();
        }
    }

    @Override
    public Iterable<Movie> findAll(Sort sort) throws Exception {
        entities=readFile();
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
        entities = readFile();
        return Optional.ofNullable(entities.get(aLong));
    }

    @Override
    public Iterable<Movie> findAll() throws IOException, ClassNotFoundException, SQLException {
        entities=readFile();
        Set<Movie> allEntities = entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
        return allEntities;
    }

    @Override
    public void setAll(Map<Long, Movie> m) throws ClassNotFoundException, ParserConfigurationException, TransformerException, IOException, SQLException {
        entities=m;
        this.writeFile();
    }

    @Override
    public Optional<Movie> save(Movie entity) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        validator.validate(entity);
        entities = readFile();
        Optional<Movie> optional =  Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
        writeFile();
        return optional;
    }

    @Override
    public Optional<Movie> delete(Long aLong) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        if (aLong == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        entities = readFile();
        Optional<Movie>  opt = Optional.ofNullable(entities.remove(aLong));
        writeFile();
        return opt;
    }

    @Override
    public Optional<Movie> update(Movie entity) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        validator.validate(entity);
        entities = readFile();
        Optional<Movie> r = Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
        writeFile();
        return r;
    }

    @Override
    public Map<Long, Movie> getAll() throws IOException, ClassNotFoundException, SQLException {
        entities=readFile();
        return entities;
    }
}
