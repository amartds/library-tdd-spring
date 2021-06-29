package andremartds.library.api.errors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ApiErrors {

    private List<String> errors;

    public ApiErrors(BindingResult bindresult) {
        this.errors = new ArrayList<>();
        bindresult.getAllErrors().forEach(result -> this.errors.add(result.getDefaultMessage()));
    }

    public List<String> getErrors() {
        return errors;
    }
}
