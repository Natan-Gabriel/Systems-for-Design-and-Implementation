package core.domain.Validators;

import core.domain.Rental;

public class RentalValidator implements Validator<Rental> {
    public void validate(Rental entity) throws ValidatorException {
        if(entity.getRentalSerialNumber().isEmpty()) throw new ValidatorException("Invalid serial!");
        if(entity.getClientID()<0) throw new ValidatorException("Invalid client ID!");
        if(entity.getMovieID()<0) throw new ValidatorException("Invalid movie ID!");
    }

}