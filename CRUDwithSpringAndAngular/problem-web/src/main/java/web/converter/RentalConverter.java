package web.converter;

import core.domain.Rental;
import org.springframework.stereotype.Component;
import web.dto.RentalDto;
@Component
public class RentalConverter extends BaseConverter<Rental, RentalDto> {
    @Override
    public Rental convertDtoToModel(RentalDto dto) {
        Rental rental = Rental.builder()
                .rentalSerialNumber(dto.getRentalSerialNumber())
                .clientID(dto.getClientID())
                .movieID(dto.getMovieID())
                .build();
        rental.setId(dto.getId());
        return rental;
    }

    @Override
    public RentalDto convertModelToDto(Rental rental) {
        RentalDto dto = RentalDto.builder()
                .rentalSerialNumber(rental.getRentalSerialNumber())
                .clientID(rental.getClientID())
                .movieID(rental.getMovieID())
                .build();
        dto.setId(rental.getId());
        return dto;
    }
}
