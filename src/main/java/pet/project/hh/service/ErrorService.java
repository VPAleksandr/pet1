package pet.project.hh.service;

import pet.project.hh.exceptions.ErrorResponseBody;
import org.springframework.validation.BindingResult;

public interface ErrorService {

    ErrorResponseBody makeResponse(Exception ex);

    ErrorResponseBody makeResponse(BindingResult bindingResult);
}
