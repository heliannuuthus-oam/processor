package io.ghcr.heliannuuthus.devtools.crypto.parameters;

public interface OamParameters {

  String AES_ALGORITHM = "AES";
  String SM4_ALGORITHM = "SM4";
  String RSA_ALGORITHM = "RSA";
  String ED_DSA_ALGORITHM = "EdDSA";
  String ECDSA_ALGORITHM = "ECDSA";
  String SM2_ALGORITHM = "SM2";

  String ECIES_ALGORITHM = "ECIES";
  String ECDH_ALGORITHM = "ECDH";

  String CONNECTOR = "with";

  String getAlgorithm();
}
