package andremartds.library.api.errors;

import andremartds.library.api.exceptions.BusinessException;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApiErrors {

    private List<String> errors;

    public ApiErrors(BindingResult bindresult) {
        this.errors = new ArrayList<>();
        bindresult.getAllErrors().forEach(result -> this.errors.add(result.getDefaultMessage()));
    }

    public ApiErrors(BusinessException ex) {
        this.errors = Collections.singletonList(ex.getMessage());
    }

    public List<String> getErrors() {
        return errors;
    }
}
