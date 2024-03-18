package io.ghcr.heliannuuthus.devtools;

import static io.ghcr.heliannuuthus.devtools.crypto.algorithms.MessageDigest.*;

import io.ghcr.heliannuuthus.devtools.crypto.Signature;
import io.ghcr.heliannuuthus.devtools.crypto.algorithms.MessageDigest;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.ecdsa.ECParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.eddsa.Ed25519Parameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.rsa.RSAParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.sm2.SM2Parameters;
import io.ghcr.heliannuuthus.devtools.provider.AsymmetricKeyProvider;
import io.ghcr.heliannuuthus.devtools.provider.parameters.ECKeyGenParameters;
import io.ghcr.heliannuuthus.devtools.provider.parameters.EdDSAKeyGenParameters;
import io.ghcr.heliannuuthus.devtools.provider.parameters.RSAKeyGenParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;
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
  private final AsymmetricKeyProvider provider = new AsymmetricKeyProvider();

  @BeforeAll
  static void init() {
    Security.addProvider(new BouncyCastleProvider());
  }

  @Test
  @DisplayName("test EdDSA ed25519")
  void testEdDSA() {
    Pair<byte[], byte[]> keys = provider.generate(new EdDSAKeyGenParameters());
    byte[] plaintext = "plaintext".getBytes();
    byte[] signature =
        this.signature.sign(plaintext, new Ed25519Parameters(keys.getKey(), keys.getValue()));
    Assertions.assertTrue(
        this.signature.verify(plaintext, signature, new Ed25519Parameters(keys.getValue(), false)));
  }

  static Stream<Arguments> ecdsaGenerator() {
    return Stream.of("secp256r1", "secp384r1", "secp521r1", "secp256k1")
        .flatMap(
            curveName ->
                Stream.of(NONE, SHA_256, SHA_384, SHA_512).map(md -> Arguments.of(curveName, md)));
  }

  @ParameterizedTest
  @MethodSource("ecdsaGenerator")
  @DisplayName("test ECDSA")
  void testECDSA(String curveName, MessageDigest md) {
    Pair<byte[], byte[]> keys = provider.generate(new ECKeyGenParameters(curveName));
    byte[] plaintext = "plaintext".getBytes();
    byte[] signature =
        this.signature.sign(plaintext, new ECParameters(keys.getKey(), keys.getValue()).md(md));
    Assertions.assertTrue(
        this.signature.verify(
            plaintext, signature, new ECParameters(keys.getValue(), false).md(md)));
  }

  static Stream<Arguments> smGenerator() {
    return Stream.of("sm2p256v1")
        .flatMap(curveName -> Stream.of(SM3, SHA_256).map(md -> Arguments.of(curveName, md)));
  }

  @ParameterizedTest
  @MethodSource("smGenerator")
  @DisplayName("test SM")
  void testSM(String curveName, MessageDigest md)
      throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
    Pair<byte[], byte[]> keys = provider.generate(new ECKeyGenParameters(curveName));
    byte[] plaintext = "plaintext".getBytes();
    byte[] signature =
        this.signature.sign(plaintext, new SM2Parameters(keys.getKey(), keys.getValue()).md(md));
    Assertions.assertTrue(
        this.signature.verify(
            plaintext, signature, new SM2Parameters(keys.getValue(), false).md(md)));
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
    Pair<byte[], byte[]> keys = provider.generate(new RSAKeyGenParameters(size));

    byte[] plaintext = "plaintext".getBytes();
    byte[] signature =
        this.signature.sign(plaintext, new RSAParameters(keys.getKey(), keys.getValue()).md(md));
    Assertions.assertTrue(
        this.signature.verify(
            plaintext, signature, new RSAParameters(keys.getValue(), false).md(md)));
  }
}
