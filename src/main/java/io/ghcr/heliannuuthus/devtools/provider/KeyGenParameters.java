package io.ghcr.heliannuuthus.devtools.provider;

import java.security.spec.AlgorithmParameterSpec;

public interface KeyGenParameters {

  String EC_ALGORITHM = "EC";

  String getName();

  default AlgorithmParameterSpec getSpec() {
    return null;
  }

  default int getSize() {
    return -1;
  }
}
