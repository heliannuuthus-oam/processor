package io.ghcr.heliannuuthus.devtools.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CryptoException extends ResponseStatusException {

  public CryptoException(int mode, String algorithm) {
    super(
        HttpStatus.UNPROCESSABLE_ENTITY, String.format("mode: %d, algorithm: %s", mode, algorithm));
  }

  public CryptoException(int mode, String algorithm, Throwable cause) {
    super(
        HttpStatus.UNPROCESSABLE_ENTITY,
        String.format("mode: %d, algorithm: %s", mode, algorithm),
        cause);
  }
}
