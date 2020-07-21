package web.dto;

import core.domain.Client;
import core.domain.Movie;
import lombok.*;

import javax.persistence.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ClientMovieDto extends BaseDto {
//    private Client client;
//    private Movie movie;
    private Long client;
    private Long movie;
    private String rentalSerialNumber;
    private boolean returned;

}
