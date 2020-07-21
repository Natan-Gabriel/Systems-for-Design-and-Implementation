package domain.Validators;

import domain.Entities.Client;

public class ClientValidator implements Validator<Client>{
    @Override
    public void validate(Client entity) throws ValidatorException {
        if(entity.getName().isEmpty()) throw new ValidatorException("Empty name!");
        if(entity.getSerial().isEmpty()) throw new ValidatorException("Empty serial!");
        if(entity.getAge()<0) throw new ValidatorException("Invalid age!");
    }
}
