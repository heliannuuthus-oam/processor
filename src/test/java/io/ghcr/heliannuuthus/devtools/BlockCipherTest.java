package io.ghcr.heliannuuthus.devtools;

import static io.ghcr.heliannuuthus.devtools.crypto.algorithms.OamAlgorithm.AES_ALGORITHM;
import static io.ghcr.heliannuuthus.devtools.crypto.parameters.AESParameters.*;

import io.ghcr.heliannuuthus.devtools.crypto.parameters.AESGCMParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.AESParameters;
import io.ghcr.heliannuuthus.devtools.crypto.symmetric.BlockCipher;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Objects;
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
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BlockCipherTest {

  private static final BlockCipher blockCipher = new BlockCipher();

  @BeforeAll
  static void init() {
    Security.addProvider(new BouncyCastleProvider());
  }

  static Stream<Arguments> buildAESCipherParameters() {
    return Stream.of(CBC_MODE, GCM_MODE)
        .flatMap(mode -> Stream.of(128, 192, 256).map(size -> Arguments.of(mode, size)));
  }

  @ParameterizedTest
  @MethodSource("buildAESCipherParameters")
  @DisplayName("test AES CBC encryption")
  void testAESCBCEncryption(String mode, int size) throws NoSuchAlgorithmException {
    byte[] plaintext = "plaintext".getBytes();
    KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
    keyGenerator.init(size);
    SecretKey secretKey = keyGenerator.generateKey();
    AESParameters aesParameters =
        Objects.equals(GCM_MODE, mode)
            ? new AESGCMParameters(secretKey.getEncoded(), generateIv(12), generateIv(128))
            : new AESParameters(secretKey.getEncoded(), mode);
    byte[] cipher = blockCipher.encrypt(plaintext, aesParameters);
    Assertions.assertArrayEquals(plaintext, blockCipher.decrypt(cipher, aesParameters));
  }
}
