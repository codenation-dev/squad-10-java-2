package br.com.codenation.log.endpoints.advice;

public class LogNotFoundException extends RuntimeException {
    public LogNotFoundException(Long id) {
        super(String.format("Log id %d not found.", id));
    }
}
