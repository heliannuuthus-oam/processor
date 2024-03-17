package io.ghcr.heliannuuthus.devtools.crypto.algorithms;

public interface OamAlgorithm {

  String AES_ALGORITHM = "AES";
  String SM4_ALGORITHM = "SM4";
  String RSA_ALGORITHM = "RSA";
  String ED_DSA_ALGORITHM = "EdDSA";

  String getAlgorithm();
}
