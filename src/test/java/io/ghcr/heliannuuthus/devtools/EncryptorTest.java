package io.ghcr.heliannuuthus.devtools;

import static io.ghcr.heliannuuthus.devtools.crypto.parameters.EncryptionParameters.*;
import static io.ghcr.heliannuuthus.devtools.utils.CryptoUtils.nextBytes;

import io.ghcr.heliannuuthus.devtools.crypto.Encryptor;
import io.ghcr.heliannuuthus.devtools.crypto.algorithms.RSAEncryptionPadding;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.EncryptionParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.aes.AESParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.ecdsa.ECIESParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.rsa.RSAEncrpytionParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.sm4.SM4CBCParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.sm4.SM4ECBParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.sm4.SM4GCMParameters;
import io.ghcr.heliannuuthus.devtools.provider.AsymmetricKeyProvider;
import io.ghcr.heliannuuthus.devtools.provider.SymmetricKeyProvider;
import io.ghcr.heliannuuthus.devtools.provider.parameters.AESKeyGenParameters;
import io.ghcr.heliannuuthus.devtools.provider.parameters.ECKeyGenParameters;
import io.ghcr.heliannuuthus.devtools.provider.parameters.RSAKeyGenParameters;
import io.ghcr.heliannuuthus.devtools.provider.parameters.SM4KeyGenParameters;
import java.security.Security;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EncryptorTest {

  private static final Encryptor ENCRYPTOR = new Encryptor();
  private static final SymmetricKeyProvider SYMMETRIC_KEY_PROVIDER = new SymmetricKeyProvider();
  private static final AsymmetricKeyProvider ASYMMETRIC_KEY_PROVIDER = new AsymmetricKeyProvider();

  @BeforeAll
  static void init() {
    Security.addProvider(new BouncyCastleProvider());
  }

  static Stream<Arguments> aesGenerator() {
    return Stream.of(CBC_MODE, GCM_MODE, ECB_MODE)
        .flatMap(mode -> Stream.of(128, 192, 256).map(size -> Arguments.of(mode, size)));
  }

  @ParameterizedTest
  @MethodSource("aesGenerator")
  @DisplayName("test AES encryption")
  void testAESEncryption(String mode, int size) {
    byte[] plaintext = "plaintext".getBytes();
    byte[] key = SYMMETRIC_KEY_PROVIDER.generate(new AESKeyGenParameters(size));
    EncryptionParameters blockParameters = new AESParameters(mode, key);
    byte[] cipher = ENCRYPTOR.encrypt(plaintext, blockParameters);
    Assertions.assertArrayEquals(plaintext, ENCRYPTOR.decrypt(cipher, blockParameters));
  }

  @ParameterizedTest
  @ValueSource(strings = {CBC_MODE, GCM_MODE, ECB_MODE})
  @DisplayName("test SM4 encryption")
  void testSM4Encryption(String mode) {
    byte[] plaintext = "plaintext".getBytes();
    byte[] key = SYMMETRIC_KEY_PROVIDER.generate(new SM4KeyGenParameters());
    EncryptionParameters blockParameters;
    switch (mode) {
      case ECB_MODE -> {
        blockParameters = new SM4ECBParameters(key);
      }
      case CBC_MODE -> {
        blockParameters = new SM4CBCParameters(key);
      }
      case GCM_MODE -> {
        blockParameters = new SM4GCMParameters(key, nextBytes(12), nextBytes(128));
      }
      default -> throw new UnsupportedOperationException();
    }
    byte[] cipher = ENCRYPTOR.encrypt(plaintext, blockParameters);
    Assertions.assertArrayEquals(plaintext, ENCRYPTOR.decrypt(cipher, blockParameters));
  }

  static Stream<Arguments> rsaStreamEncryption() {
    return Stream.of(2048, 3072, 4096)
        .flatMap(c -> Stream.of(RSAEncryptionPadding.values()).map(cc -> Arguments.of(c, cc)));
  }

  @ParameterizedTest
  @MethodSource("rsaStreamEncryption")
  void testRSAStreamEncrypt(Integer keySize, RSAEncryptionPadding padding) {
    byte[] plaintext = "plaintext".getBytes();
    Pair<byte[], byte[]> keyPair =
        ASYMMETRIC_KEY_PROVIDER.generate(new RSAKeyGenParameters(keySize));

    EncryptionParameters encryptionParameters =
        new RSAEncrpytionParameters(keyPair.getRight(), false).padding(padding);
    byte[] cipher = ENCRYPTOR.encrypt(plaintext, encryptionParameters);

    Assertions.assertArrayEquals(
        plaintext,
        ENCRYPTOR.decrypt(
            cipher, new RSAEncrpytionParameters(keyPair.getLeft(), true).padding(padding)));
  }

  static Stream<Arguments> eciesStreamEncrypt() {
    return Stream.of("secp256r1", "secp384r1", "secp521r1", "secp256k1", "curve25519")
        .map(Arguments::of);
  }

  @ParameterizedTest
  @MethodSource("eciesStreamEncrypt")
  void testECIESStreamEncrypt(String curveName) {
    byte[] plaintext = "plaintext".getBytes();
    Pair<byte[], byte[]> keyPair =
        ASYMMETRIC_KEY_PROVIDER.generate(new ECKeyGenParameters(curveName));

    EncryptionParameters encryptionParameters = new ECIESParameters(keyPair.getRight(), false);
    byte[] cipher = ENCRYPTOR.encrypt(plaintext, encryptionParameters);

    Assertions.assertArrayEquals(
        plaintext, ENCRYPTOR.decrypt(cipher, new ECIESParameters(keyPair.getLeft(), true)));
  }
}
