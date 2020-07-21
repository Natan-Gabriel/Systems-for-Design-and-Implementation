package domain.Validators;

import domain.Movie;

public class MovieValidator implements Validator<Movie>{
    @Override
    public void validate(Movie entity) throws ValidatorException {
        if(entity.getName().isEmpty()) throw new ValidatorException("Empty name!");
        if(entity.getSerial().isEmpty()) throw new ValidatorException("Empty serial!");
        if(entity.getRating()<0 || entity.getRating()>100) throw new ValidatorException("Invalid rating!");
        if(entity.getYear()<1900 || entity.getYear()>2100) throw new ValidatorException("Invalid year!");
    }
}
