package core.domain;

import lombok.*;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client_movie")
@IdClass(ClientMoviePK.class)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class ClientMovie implements Serializable {
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "rentalSerialNumber")
    private String rentalSerialNumber;
    //@Getter
    @Column(name = "returned")
    private boolean returned;

    @Override
    public String toString() {
        return "ClientMovie{" +
                "client=" + client.getId() +
                ", movie=" + movie.getId() +
                ", rentalSerialNumber=" + rentalSerialNumber +
                '}';
    }
}