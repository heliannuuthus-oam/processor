package io.ghcr.heliannuuthus.devtools.provider.parameters;

import static io.ghcr.heliannuuthus.devtools.crypto.parameters.OamParameters.RSA_ALGORITHM;

import io.ghcr.heliannuuthus.devtools.provider.KeyGenParameters;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;

public class RSAKeyGenParameters implements KeyGenParameters {

  public RSAKeyGenParameters(int keySize) {
    this.parameterSpec = new RSAKeyGenParameterSpec(keySize, RSAKeyGenParameterSpec.F4);
  }

  public RSAKeyGenParameters(int keySize, BigInteger publicExponent) {
    this.parameterSpec = new RSAKeyGenParameterSpec(keySize, publicExponent);
  }

  private final RSAKeyGenParameterSpec parameterSpec;

  @Override
  public String getName() {
    return RSA_ALGORITHM;
  }

  @Override
  public AlgorithmParameterSpec getSpec() {
    return parameterSpec;
  }
}
