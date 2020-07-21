package core.domain;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
class ClientMoviePK implements Serializable {
    private Client client;
    private Movie movie;
}
