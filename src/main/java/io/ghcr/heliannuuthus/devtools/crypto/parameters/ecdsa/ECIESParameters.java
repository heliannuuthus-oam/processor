package io.ghcr.heliannuuthus.devtools.crypto.parameters.ecdsa;

import io.ghcr.heliannuuthus.devtools.crypto.parameters.StreamEncryptionParameters;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.jce.spec.IESParameterSpec;
import org.bouncycastle.util.encoders.Hex;

public class ECIESParameters extends StreamEncryptionParameters {

  private static final byte[] DERIVATION = Hex.decode("202122232425262728292a2b2c2d2e2f");
  private static final byte[] ENCODING = Hex.decode("303132333435363738393a3b3c3d3e3f");

  protected ECIESParameters() {
    super();
  }

  public ECIESParameters(byte[] key, boolean isPrivate) {
    super(key, isPrivate);
  }

  @Override
  public String getName() {
    return ECDH_ALGORITHM;
  }

  @Override
  public String getMode() {
    return null;
  }

  @Override
  public AlgorithmParameterSpec getSpec() {
    return new IESParameterSpec(DERIVATION, ENCODING, 128);
  }

  @Override
  public String getAlgorithm() {
    return ECIES_ALGORITHM;
  }
}
