package io.ghcr.heliannuuthus.devtools;

import io.ghcr.heliannuuthus.devtools.crypto.Signature;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.eddsa.Ed25519Parameters;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
  void testEd25519() throws NoSuchAlgorithmException {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(EdDSAParameterSpec.Ed25519);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    byte[] privateKey = keyPair.getPrivate().getEncoded();
    byte[] publicKey = keyPair.getPublic().getEncoded();
    byte[] plaintext = "plaintext".getBytes();
    byte[] signature = this.signature.sign(plaintext, new Ed25519Parameters(privateKey, publicKey));
    Assertions.assertTrue(
        this.signature.verify(plaintext, signature, new Ed25519Parameters(publicKey, false)));
  }
}
