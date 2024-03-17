package io.ghcr.heliannuuthus.devtools;

import static io.ghcr.heliannuuthus.devtools.crypto.parameters.BlockParameters.ECB_MODE;
import static io.ghcr.heliannuuthus.devtools.crypto.parameters.OamParameters.AES_ALGORITHM;
import static io.ghcr.heliannuuthus.devtools.crypto.parameters.aes.AESCBCParameters.CBC_MODE;
import static io.ghcr.heliannuuthus.devtools.crypto.parameters.aes.AESCBCParameters.GCM_MODE;
import static io.ghcr.heliannuuthus.devtools.utils.CryptoUtils.nextBytes;

import io.ghcr.heliannuuthus.devtools.crypto.BlockCipher;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.BlockParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.aes.AESCBCParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.aes.AESECBParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.aes.AESGCMParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.sm4.SM4CBCParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.sm4.SM4ECBParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.sm4.SM4GCMParameters;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.stream.Stream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
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
class BlockCipherTest {

  private static final BlockCipher blockCipher = new BlockCipher();

  @BeforeAll
  static void init() {
    Security.addProvider(new BouncyCastleProvider());
  }

  static Stream<Arguments> buildAESCipherParameters() {
    return Stream.of(CBC_MODE, GCM_MODE, ECB_MODE)
        .flatMap(mode -> Stream.of(128, 192, 256).map(size -> Arguments.of(mode, size)));
  }

  @ParameterizedTest
  @MethodSource("buildAESCipherParameters")
  @DisplayName("test AES encryption")
  void testAESEncryption(String mode, int size) throws NoSuchAlgorithmException {
    byte[] plaintext = "plaintext".getBytes();
    KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
    keyGenerator.init(size);
    SecretKey secretKey = keyGenerator.generateKey();
    BlockParameters blockParameters;
    switch (mode) {
      case ECB_MODE -> {
        blockParameters = new AESECBParameters(secretKey.getEncoded());
      }
      case CBC_MODE -> {
        blockParameters = new AESCBCParameters(secretKey.getEncoded());
      }
      case GCM_MODE -> {
        blockParameters =
            new AESGCMParameters(secretKey.getEncoded(), nextBytes(12), nextBytes(128));
      }
      default -> throw new UnsupportedOperationException();
    }
    byte[] cipher = blockCipher.encrypt(plaintext, blockParameters);
    Assertions.assertArrayEquals(plaintext, blockCipher.decrypt(cipher, blockParameters));
  }

  @ParameterizedTest
  @ValueSource(strings = {CBC_MODE, GCM_MODE, ECB_MODE})
  @DisplayName("test SM4 encryption")
  void testSM4Encryption(String mode) throws NoSuchAlgorithmException {
    byte[] plaintext = "plaintext".getBytes();
    KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
    keyGenerator.init(128);
    SecretKey secretKey = keyGenerator.generateKey();
    BlockParameters blockParameters;
    switch (mode) {
      case ECB_MODE -> {
        blockParameters = new SM4ECBParameters(secretKey.getEncoded());
      }
      case CBC_MODE -> {
        blockParameters = new SM4CBCParameters(secretKey.getEncoded());
      }
      case GCM_MODE -> {
        blockParameters =
            new SM4GCMParameters(secretKey.getEncoded(), nextBytes(12), nextBytes(128));
      }
      default -> throw new UnsupportedOperationException();
    }
    byte[] cipher = blockCipher.encrypt(plaintext, blockParameters);
    Assertions.assertArrayEquals(plaintext, blockCipher.decrypt(cipher, blockParameters));
  }
}
