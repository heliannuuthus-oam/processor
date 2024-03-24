package io.ghcr.heliannuuthus.devtools.crypto.parameters.eddsa;

import io.ghcr.heliannuuthus.devtools.crypto.parameters.AsymmetricParameters;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;

public class Ed448Parameters extends AsymmetricParameters {

  protected Ed448Parameters() {
    super();
  }

  public Ed448Parameters(byte[] key, boolean isPrivate) {
    super(key, isPrivate);
  }

  public Ed448Parameters(byte[] privateKey, byte[] publicKey) {
    super(privateKey, publicKey);
  }

  @Override
  public String getName() {
    return EdDSAParameterSpec.Ed448;
  }

  public AlgorithmParameterSpec getSpec() {
    return new EdDSAParameterSpec(getName());
  }

  @Override
  public String getAlgorithm() {
    return ED_DSA_ALGORITHM;
  }
}
