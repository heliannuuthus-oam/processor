package io.ghcr.heliannuuthus.devtools.constants;

import static io.ghcr.heliannuuthus.devtools.provider.AsymmetricKeyProvider.ASYMMETRIC;
import static io.ghcr.heliannuuthus.devtools.provider.SymmetricKeyProvider.SYMMETRIC;
import static org.bouncycastle.jcajce.spec.EdDSAParameterSpec.Ed25519;
import static org.bouncycastle.jcajce.spec.EdDSAParameterSpec.Ed448;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeySpec {
  AES_128("AES_128", SYMMETRIC, 16),
  AES_256("AES_256", SYMMETRIC, 32),
  SM4("SM4", SYMMETRIC, 16),
  RSA_2048("RSA_2048", ASYMMETRIC, 256),
  RSA_3072("RSA_3072", ASYMMETRIC, 384),
  RSA_4096("RSA_4096", ASYMMETRIC, 512),
  EC_P256("EC-P256", ASYMMETRIC, 126),
  EC_P384("EC-P384", ASYMMETRIC, 384),
  EC_P521("EC-P521", ASYMMETRIC, 521),
  EC_P256K("EC-P256K", ASYMMETRIC, 256),
  ED_25519(Ed25519, ASYMMETRIC, 256),
  ED_448(Ed448, ASYMMETRIC, 57);
  private String value;
  private String type;
  private int size;
}
