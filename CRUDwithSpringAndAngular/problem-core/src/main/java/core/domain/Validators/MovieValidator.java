package core.domain.Validators;

import core.domain.Movie;

public class MovieValidator implements Validator<Movie> {
    public void validate(Movie entity) throws ValidatorException {
        if(entity.getName().isEmpty()) throw new ValidatorException("Empty name!");
        if(entity.getSerialNumber().isEmpty()) throw new ValidatorException("Empty serial!");
        if(entity.getRating()<0 || entity.getRating()>100) throw new ValidatorException("Invalid rating!");
        if(entity.getYear()<1900 || entity.getYear()>2100) throw new ValidatorException("Invalid year!");
    }

}
