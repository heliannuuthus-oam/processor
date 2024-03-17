package io.ghcr.heliannuuthus.devtools.crypto.algorithms;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageDigest {
  NONE("None"),
  SM3("SM3"),
  SHA_256("SHA256"),
  SHA_384("SHA384"),
  SHA_512("SHA512");

  private String name;
}
