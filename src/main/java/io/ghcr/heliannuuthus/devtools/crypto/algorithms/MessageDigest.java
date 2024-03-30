package io.ghcr.heliannuuthus.devtools.crypto.algorithms;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageDigest {
  NONE("None"),
  SM3("SM3"),
  SHA_1("SHA1"),
  SHA_256("SHA256"),
  SHA_384("SHA384"),
  SHA_512("SHA512"),
  SHA3_256("SHA3-256"),
  SHA3_384("SHA3-384"),
  SHA3_512("SHA3-512");
  private final String value;
}
