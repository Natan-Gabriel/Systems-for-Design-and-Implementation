package core.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.*;


@NamedEntityGraphs({
        @NamedEntityGraph(name = "clientWithRentals",
                attributeNodes = @NamedAttributeNode(value = "rentals")),


        @NamedEntityGraph(name = "clientWithRentalsAndMovie",
                attributeNodes = @NamedAttributeNode(value = "rentals",
                        subgraph = "rentalWithMovie"),
                subgraphs = @NamedSubgraph(name = "rentalWithMovie",
                        attributeNodes = @NamedAttributeNode(value =
                                "movie")))
})
@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
//@Data
//@EqualsAndHashCode(callSuper = true)
//@ToString(callSuper = true)
@Getter
@Setter
@Builder
public class Client extends BaseEntity<Long> implements Serializable {
    @Column(name = "serial_number", nullable = false)
    private String serialNumber;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "age", nullable = false)
    private int age;

    @OneToMany(mappedBy = "client",orphanRemoval = true, cascade = CascadeType.ALL, fetch =
            FetchType.LAZY)
    private Set<ClientMovie> rentals = new HashSet<>();


    public Set<Movie> getMovies() {
        rentals = rentals == null ? new HashSet<>() :
                rentals;
        return Collections.unmodifiableSet(
                this.rentals.stream().
                        map(ClientMovie::getMovie).
                        collect(Collectors.toSet()));
    }

    /*
    public Client() {
        //super.getId();
    }

    public Client(String name, int age, String serial) {
        //super();
        this.name = name;
        this.age = age;
        this.serialNumber=serial;

    }

    public String getName() {
        return this.name;
    }

    public void setName(String new_name) {
        this.name = new_name;
    }

    public String getSerial() {
        return this.serialNumber;
    }

    public void setSerial(String new_serial) {
        this.serialNumber = new_serial;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String new_serial) {
        this.serialNumber = new_serial;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int new_age) {
        this.age = new_age;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this==o){
            return true;
        }
        if(o==null || getClass() != o.getClass()){
            return false;
        }

        Client cli = (Client) o;

        if (!this.name.equals(cli.name)) return false;
        if(!this.serialNumber.equals(cli.serialNumber)) return false;
        return (this.age==cli.age);
    }

    @Override
    public String toString()
    {
        return "Client{ "+"serial: "+serialNumber +", name: "+
                name + ", age: "+ age +"} "+ super.toString();
    }

    @Override
    public int hashCode()
    {
        int result = serialNumber.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + age;
        return result;
    }*/
}
