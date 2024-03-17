package io.ghcr.heliannuuthus.devtools;

import static io.ghcr.heliannuuthus.devtools.crypto.algorithms.MessageDigest.*;
import static io.ghcr.heliannuuthus.devtools.crypto.parameters.OamParameters.ECDSA_ALGORITHM;
import static io.ghcr.heliannuuthus.devtools.crypto.parameters.OamParameters.RSA_ALGORITHM;

import io.ghcr.heliannuuthus.devtools.crypto.Signature;
import io.ghcr.heliannuuthus.devtools.crypto.algorithms.MessageDigest;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.ecdsa.ECParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.eddsa.Ed25519Parameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.rsa.RSAParameters;
import java.security.*;
import java.util.stream.Stream;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SignatureTest {
  private final Signature signature = new Signature();

  @BeforeAll
  static void init() {
    Security.addProvider(new BouncyCastleProvider());
  }

  @Test
  @DisplayName("test EdDSA ed25519")
  void testEdDSA() throws NoSuchAlgorithmException, NoSuchProviderException {
    KeyPairGenerator keyPairGenerator =
        KeyPairGenerator.getInstance(
            EdDSAParameterSpec.Ed25519, BouncyCastleProvider.PROVIDER_NAME);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    byte[] privateKey = keyPair.getPrivate().getEncoded();
    byte[] publicKey = keyPair.getPublic().getEncoded();
    byte[] plaintext = "plaintext".getBytes();
    byte[] signature = this.signature.sign(plaintext, new Ed25519Parameters(privateKey, publicKey));
    Assertions.assertTrue(
        this.signature.verify(plaintext, signature, new Ed25519Parameters(publicKey, false)));
  }

  static Stream<Arguments> ecdsaGenerator() {
    return Stream.of("secp256r1", "secp384r1", "secp521r1", "secp256k1")
        .flatMap(
            curveName ->
                Stream.of(NONE, SHA_256, SHA_384, SHA_512).map(md -> Arguments.of(curveName, md)));
  }

  @ParameterizedTest
  @MethodSource("ecdsaGenerator")
  @DisplayName("test EdDSA")
  void testECDSA(String curveName, MessageDigest md)
      throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
    KeyPairGenerator keyPairGenerator =
        KeyPairGenerator.getInstance(ECDSA_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
    keyPairGenerator.initialize(ECNamedCurveTable.getParameterSpec(curveName));
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    byte[] privateKey = keyPair.getPrivate().getEncoded();
    byte[] publicKey = keyPair.getPublic().getEncoded();
    byte[] plaintext = "plaintext".getBytes();
    byte[] signature =
        this.signature.sign(plaintext, new ECParameters(privateKey, publicKey).md(md));
    Assertions.assertTrue(
        this.signature.verify(plaintext, signature, new ECParameters(publicKey, false).md(md)));
  }

  static Stream<Arguments> rsaGenerator() {
    return Stream.of(2048, 3072, 4096)
        .flatMap(
            curveName ->
                Stream.of(NONE, SHA_256, SHA_384, SHA_512).map(md -> Arguments.of(curveName, md)));
  }

  @ParameterizedTest
  @MethodSource("rsaGenerator")
  @DisplayName("test RSA")
  void testECDSA(int size, MessageDigest md)
      throws NoSuchAlgorithmException, NoSuchProviderException {
    KeyPairGenerator keyPairGenerator =
        KeyPairGenerator.getInstance(RSA_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
    keyPairGenerator.initialize(size);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    byte[] privateKey = keyPair.getPrivate().getEncoded();
    byte[] publicKey = keyPair.getPublic().getEncoded();
    byte[] plaintext = "plaintext".getBytes();
    byte[] signature =
        this.signature.sign(plaintext, new RSAParameters(privateKey, publicKey).md(md));
    Assertions.assertTrue(
        this.signature.verify(plaintext, signature, new RSAParameters(publicKey, false).md(md)));
  }
}
