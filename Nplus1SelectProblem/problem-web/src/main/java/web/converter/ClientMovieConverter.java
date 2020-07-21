package web.converter;

import core.domain.ClientMovie;
import org.springframework.stereotype.Component;
import web.dto.ClientMovieDto;
import web.dto.RentalDto;

@Component
public class ClientMovieConverter extends AbstractConverter<ClientMovie, ClientMovieDto>{
    @Override
    public ClientMovie convertDtoToModel(ClientMovieDto dto) {
//        ClientMovie rental = ClientMovie.builder()
//                .rentalSerialNumber(dto.getRentalSerialNumber())
//                .client(dto.getClientId())
//                .movieID(dto.getMovieID())
//                .build();
//        rental.setId(dto.getId());
//        return rental;
        return new ClientMovie();
    }

    @Override
    public ClientMovieDto convertModelToDto(ClientMovie clientMovie) {
        ClientMovieDto dto = ClientMovieDto.builder()
                .rentalSerialNumber(clientMovie.getRentalSerialNumber())
                .client(clientMovie.getClient().getId())
                .movie(clientMovie.getMovie().getId())
                .returned(clientMovie.isReturned())
                .build();
        return dto;
    }

}
