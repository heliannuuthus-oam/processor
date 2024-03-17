package io.ghcr.heliannuuthus.devtools.crypto.parameters;

public class ECBParameters extends BlockParameters {

  @Override
  public String getMode() {
    return ECB_MODE;
  }
}
