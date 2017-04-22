package com.platform.api.controller;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 28.12.16.
 */
@ControllerAdvice
public class ExceptionController {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionController.class);


    @ExceptionHandler(PlatformException.class)
    public ResponseEntity<PlatformErrorDto> handlePlatformException(PlatformException ex) {
        logger.error("PlatformException caught. {}", ex.getMessage());
        PlatformErrorDto errorEntity = new PlatformErrorDto("error.general", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorEntity);
    }


    @ExceptionHandler(AlgorithmException.class)
    public ResponseEntity<PlatformErrorDto> handlePlatformException(AlgorithmException ex) {
        logger.error("AlgorithmException caught. {}", ex.getMessage());
        PlatformErrorDto errorEntity = new PlatformErrorDto("error.algorithm", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorEntity);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<PlatformErrorDto> handleAuthenticationException(AuthenticationException ex) {
        logger.error("AuthenticationException caught. {}", ex.getMessage());
        PlatformErrorDto errorEntity = new PlatformErrorDto("error.auth", "You have to login first");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorEntity);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<PlatformErrorDto> handleBadCredentialsException(BadCredentialsException ex) {
        logger.error("BadCredentialsException caught: {}", ex.getMessage());
        PlatformErrorDto errorEntity = new PlatformErrorDto("error.auth.data", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorEntity);
    }


    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<PlatformErrorDto> handleInvalidFormatException(InvalidFormatException ex) {
        logger.error("InvalidFormatException caught: {}", ex.getMessage());
        PlatformErrorDto errorEntity = new PlatformErrorDto("error.input", "Incorrect data provided");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorEntity);
    }


    @ExceptionHandler(PlatformFieldValidationException.class)
    public ResponseEntity<List<PlatformFieldErrorDto>> handlePlatformFieldValidationException(
            PlatformFieldValidationException ex)
    {
        logger.warn("PlatformFieldValidationException caught");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrors());
    }
}