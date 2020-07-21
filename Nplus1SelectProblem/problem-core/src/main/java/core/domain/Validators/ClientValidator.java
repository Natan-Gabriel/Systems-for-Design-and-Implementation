package core.domain.Validators;

import core.domain.Client;

public class ClientValidator implements Validator<Client> {
    public void validate(Client entity) throws ValidatorException {
        if(entity.getName().isEmpty()) throw new ValidatorException("Empty name!");
        if(entity.getSerialNumber().isEmpty()) throw new ValidatorException("Empty serial!");
        if(entity.getAge()<0) throw new ValidatorException("Invalid age!");
    }

}
