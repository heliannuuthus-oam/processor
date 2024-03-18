package io.ghcr.heliannuuthus.devtools.provider.parameters;

import static io.ghcr.heliannuuthus.devtools.crypto.parameters.OamParameters.AES_ALGORITHM;

import io.ghcr.heliannuuthus.devtools.provider.KeyGenParameters;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AESKeyGenParameters implements KeyGenParameters {
  private int keySize;

  @Override
  public String getName() {
    return AES_ALGORITHM;
  }

  @Override
  public int getSize() {
    return keySize;
  }
}
