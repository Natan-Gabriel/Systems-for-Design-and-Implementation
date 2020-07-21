package domain.Validators;

import domain.Rental;

public class RentalValidator implements Validator<Rental>{
    @Override
    public void validate(Rental entity) throws ValidatorException {
        if(entity.getRentalSerial().isEmpty()) throw new ValidatorException("Invalid serial!");
        if(entity.getClientID()<0) throw new ValidatorException("Invalid client ID!");
        if(entity.getMovieID()<0) throw new ValidatorException("Invalid movie ID!");
    }
}