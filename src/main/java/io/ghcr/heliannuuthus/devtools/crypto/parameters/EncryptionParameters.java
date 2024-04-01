package io.ghcr.heliannuuthus.devtools.crypto.parameters;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

public interface EncryptionParameters extends OamParameters {

  String NONE_MODE = "NONE";
  String ECB_MODE = "ECB";
  String CBC_MODE = "CBC";
  String GCM_MODE = "GCM";

  Key getKey();

  String getAlgorithm();

  String getMode();

  default String getName() {
    return null;
  }

  default AlgorithmParameterSpec getSpec() {
    return null;
  }
}
