package io.ghcr.heliannuuthus.devtools.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeyAlgorithm {
  AES("aes"),
  ECC("ecc"),
  ED("ed"),
  GM("gm"),
  RSA("rsa");
  private final String value;

  @Override
  public String toString() {
    return getValue();
  }
}
