package io.ghcr.heliannuuthus.devtools;

import static io.ghcr.heliannuuthus.devtools.crypto.parameters.BlockParameters.ECB_MODE;
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
import io.ghcr.heliannuuthus.devtools.provider.SymmetricKeyProvider;
import io.ghcr.heliannuuthus.devtools.provider.parameters.AESKeyGenParameters;
import io.ghcr.heliannuuthus.devtools.provider.parameters.SM4KeyGenParameters;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.stream.Stream;
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
  private static final SymmetricKeyProvider symmetricKeyProvider = new SymmetricKeyProvider();

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
  void testAESEncryption(String mode, int size) throws NoSuchAlgorithmException {
    byte[] plaintext = "plaintext".getBytes();
    byte[] key = symmetricKeyProvider.generate(new AESKeyGenParameters(128));
    BlockParameters blockParameters;
    switch (mode) {
      case ECB_MODE -> {
        blockParameters = new AESECBParameters(key);
      }
      case CBC_MODE -> {
        blockParameters = new AESCBCParameters(key);
      }
      case GCM_MODE -> {
        blockParameters = new AESGCMParameters(key, nextBytes(12), nextBytes(128));
      }
      default -> throw new UnsupportedOperationException();
    }
    byte[] cipher = blockCipher.encrypt(plaintext, blockParameters);
    Assertions.assertArrayEquals(plaintext, blockCipher.decrypt(cipher, blockParameters));
  }

  @ParameterizedTest
  @ValueSource(strings = {CBC_MODE, GCM_MODE, ECB_MODE})
  @DisplayName("test SM4 encryption")
  void testSM4Encryption(String mode) {
    byte[] plaintext = "plaintext".getBytes();
    byte[] key = symmetricKeyProvider.generate(new SM4KeyGenParameters());
    BlockParameters blockParameters;
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
    byte[] cipher = blockCipher.encrypt(plaintext, blockParameters);
    Assertions.assertArrayEquals(plaintext, blockCipher.decrypt(cipher, blockParameters));
  }
}
