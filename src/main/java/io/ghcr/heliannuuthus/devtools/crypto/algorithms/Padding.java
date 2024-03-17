package io.ghcr.heliannuuthus.devtools.crypto.algorithms;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Padding {
  PKCS7("PKCS7Padding"),
  PKCS5("PKCS5Padding"),
  None("NoPadding");
  private final String name;
}
