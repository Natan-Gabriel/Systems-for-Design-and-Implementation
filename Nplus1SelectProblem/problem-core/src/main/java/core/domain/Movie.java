package core.domain;



import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "movieWithRentals",
                attributeNodes = @NamedAttributeNode(value = "rentals")),


        @NamedEntityGraph(name = "movieWithRentalsAndClient",
                attributeNodes = @NamedAttributeNode(value = "rentals",
                        subgraph = "rentalWithClient"),
                subgraphs = @NamedSubgraph(name = "rentalWithClient",
                        attributeNodes = @NamedAttributeNode(value =
                                "client")))
})
@Entity
@NoArgsConstructor
@AllArgsConstructor
//@Data
//@EqualsAndHashCode(callSuper = true)
//@ToString(callSuper = true)
@Getter
@Setter
@Builder
public class Movie extends BaseEntity<Long> implements Serializable {
    @Column(name = "serial_number", nullable = false)
    private String serialNumber;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "year", nullable = false)
    private int year;
    @Column(name = "rating", nullable = false)
    private int rating; //out of 100

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ClientMovie> rentals = new HashSet<>();

    public Set<Client> getClients() {
        return Collections.unmodifiableSet(
                rentals.stream()
                        .map(ClientMovie::getClient)
                        .collect(Collectors.toSet())
        );
    }
    /*
    public Movie() {
    }

    public Movie(String name, int year, int rating, String serial) {
        this.name = name;
        this.year = year;
        this.rating = rating;
        this.serialNumber=serial;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String new_name) {
        this.name = new_name;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String new_serial) {
        this.serialNumber = new_serial;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int new_year) {
        this.year = new_year;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int new_rating) {
        this.rating = new_rating;
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

        Movie mov = (Movie) o;

        if (!this.name.equals(mov.name)) return false;
        if (this.year!=mov.year) return false;
        if(!this.serialNumber.equals(mov.serialNumber)) return false;
        return (this.rating==mov.rating);
    }

    @Override
    public String toString()
    {
        return "Movie{ "+"serial: "+serialNumber +", name: "+
                name + ", year: "+ year + ", rating: "+ rating + "} "+ super.toString();
    }

    @Override
    public int hashCode()
    {
        int result = serialNumber.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + rating;
        return result;
    }*/
}
