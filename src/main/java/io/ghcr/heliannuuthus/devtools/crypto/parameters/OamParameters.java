package io.ghcr.heliannuuthus.devtools.crypto.parameters;

public interface OamParameters {

  String AES_ALGORITHM = "AES";
  String SM4_ALGORITHM = "SM4";
  String RSA_ALGORITHM = "RSA";
  String ED_DSA_ALGORITHM = "EdDSA";
  String ECDSA_ALGORITHM = "ECDSA";

  String getAlgorithm();
}
