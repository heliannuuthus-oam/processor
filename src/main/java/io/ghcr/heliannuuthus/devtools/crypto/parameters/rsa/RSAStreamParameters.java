package io.ghcr.heliannuuthus.devtools.crypto.parameters.rsa;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.MessageDigest;
import io.ghcr.heliannuuthus.devtools.crypto.algorithms.RSAEncryptionPadding;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.StreamEncryptionParameters;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.util.Optional;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class RSAStreamParameters extends StreamEncryptionParameters {
  protected RSAStreamParameters() {
    super();
  }

  public RSAStreamParameters(byte[] key, boolean isPrivate) {
    super(key, isPrivate);
  }

  @Override
  public String getName() {
    return RSA_ALGORITHM;
  }

  private RSAEncryptionPadding padding = RSAEncryptionPadding.OAEP_SHA256;

  public RSAStreamParameters padding(RSAEncryptionPadding padding) {
    this.padding = padding;
    return this;
  }

  @Override
  public String getMode() {
    return NONE_MODE;
  }

  @Override
  public String getPadding() {
    return padding.getValue();
  }

  @Override
  public AlgorithmParameterSpec getSpec() {
    MessageDigest md = padding.getMd();
    return Optional.ofNullable(md)
        .map(
            cc ->
                new OAEPParameterSpec(
                    cc.getValue(), "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT))
        .orElse(null);
  }
}
