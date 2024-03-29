package io.ghcr.heliannuuthus.devtools.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CryptoException extends ResponseStatusException {

  public static final int INIT_MODE = -1;
  public static final int ENCRYPT_MODE = 1;
  public static final int DECRYPT_MODE = 2;
  public static final int SIGN_MODE = 4;
  public static final int VERIFY_MODE = 8;
  public static final int ENCODE_MODE = 16;
  public static final int DECODE_MODE = 32;

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
