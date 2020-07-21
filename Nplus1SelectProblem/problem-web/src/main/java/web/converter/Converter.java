package web.converter;

import core.domain.BaseEntity;
import web.dto.BaseDto;


/**
 * Created by radu.
 */

public interface Converter<Model, Dto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}

