package repository;

import javafx.util.Pair;
import domain.Rental;
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

public class DBRentalRepository implements SortingRepository<Long, Rental> {

    private Map<Long, Rental> entities;
    private Validator<Rental> validator;

    private static final String URL = "jdbc:postgresql://localhost:1234/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "terno";

    public DBRentalRepository(Validator<Rental> validator) {
        this.validator = validator;
    }

    private Map<Long, Rental> readFile() throws SQLException {
        Map<Long, Rental> map = new HashMap<>();
        String sql = "select * from rentals";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next())
        {
            Long id = resultSet.getLong("id");
            String serial = resultSet.getString("serial");
            Long mid = resultSet.getLong("movieid");
            Long cid = resultSet.getLong("clientid");
            boolean returned = resultSet.getBoolean("returned");

            Rental ren = new Rental(serial, mid, cid, returned);
            ren.setId(id);

            map.put(id, ren);
        }
        return map;
    }

    private void writeFile() throws SQLException, ClassNotFoundException, ParserConfigurationException, TransformerException, IOException {
        String sql = "delete from rentals";
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        for (Rental entity:entities.values()
        ) {
            String sql2 = "insert into rentals (id, serial, movieid, clientid, returned) values (?, ?, ?, ?, ?)";
            Connection connection2 = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement2 = connection2.prepareStatement(sql2);
            preparedStatement2.setLong(1, entity.getId());
            preparedStatement2.setString(2, entity.getRentalSerial());
            preparedStatement2.setLong(3, entity.getMovieID());
            preparedStatement2.setLong(4, entity.getClientID());
            preparedStatement2.setBoolean(5, entity.isReturned());
            preparedStatement2.executeUpdate();
        }
    }


    @Override
    public Iterable<Rental> findAll(Sort sort) throws SQLException {
        entities=readFile();
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
        entities = readFile();
        return Optional.ofNullable(entities.get(aLong));
    }

    @Override
    public Iterable<Rental> findAll() throws IOException, ClassNotFoundException, SQLException {
        entities=readFile();
        Set<Rental> allEntities = entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
        return allEntities;
    }

    @Override
    public void setAll(Map<Long, Rental> m) throws ClassNotFoundException, ParserConfigurationException, TransformerException, IOException, SQLException {
        entities=m;
        this.writeFile();
    }

    @Override
    public Optional<Rental> save(Rental entity) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        validator.validate(entity);
        entities = readFile();
        Optional<Rental> optional =  Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
        writeFile();
        return optional;
    }

    @Override
    public Optional<Rental> delete(Long aLong) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        if (aLong == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        entities = readFile();
        Optional<Rental>  opt = Optional.ofNullable(entities.remove(aLong));
        writeFile();
        return opt;
    }

    @Override
    public Optional<Rental> update(Rental entity) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        validator.validate(entity);
        entities = readFile();
        Optional<Rental> r = Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
        writeFile();
        return r;
    }

    @Override
    public Map<Long, Rental> getAll() throws IOException, ClassNotFoundException, SQLException {
        entities=readFile();
        return entities;
    }
}
