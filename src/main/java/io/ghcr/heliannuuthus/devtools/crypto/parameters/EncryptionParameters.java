package io.ghcr.heliannuuthus.devtools.crypto.parameters;

import com.google.common.base.Joiner;
import io.ghcr.heliannuuthus.devtools.crypto.algorithms.AESEncryptionPadding;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

public interface EncryptionParameters extends OamParameters {

  String NONE_MODE = "NONE";
  String ECB_MODE = "ECB";
  String CBC_MODE = "CBC";
  String GCM_MODE = "GCM";

  Key getKey();

  @Override
  default String getAlgorithm() {
    return Joiner.on("/").skipNulls().join(getName(), getMode(), getPadding());
  }

  default String getName() {
    return null;
  }

  String getMode();

  default String getPadding() {
    return AESEncryptionPadding.PKCS7.getValue();
  }

  default AlgorithmParameterSpec getSpec() {
    return null;
  }
}
