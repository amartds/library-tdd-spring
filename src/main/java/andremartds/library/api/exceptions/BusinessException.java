package andremartds.library.api.exceptions;

public class BusinessException extends RuntimeException{

    public BusinessException(String isbnJaExiste) {
        super(isbnJaExiste);
    }
}
