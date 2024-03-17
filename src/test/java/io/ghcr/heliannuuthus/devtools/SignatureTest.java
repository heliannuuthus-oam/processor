package io.ghcr.heliannuuthus.devtools;

import io.ghcr.heliannuuthus.devtools.crypto.Signature;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.ecdsa.ECParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.eddsa.Ed25519Parameters;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.*;

import static io.ghcr.heliannuuthus.devtools.crypto.parameters.OamParameters.ECDSA_ALGORITHM;

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
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(EdDSAParameterSpec.Ed25519, BouncyCastleProvider.PROVIDER_NAME);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        byte[] privateKey = keyPair.getPrivate().getEncoded();
        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] plaintext = "plaintext".getBytes();
        byte[] signature = this.signature.sign(plaintext, new Ed25519Parameters(privateKey, publicKey));
        Assertions.assertTrue(
                this.signature.verify(plaintext, signature, new Ed25519Parameters(publicKey, false)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"secp256r1", "secp384r1", "secp521r1", "secp256k1"})
    @DisplayName("test EdDSA")
    void testECDSA(String curveName) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ECDSA_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
        keyPairGenerator.initialize(ECNamedCurveTable.getParameterSpec(curveName));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        byte[] privateKey = keyPair.getPrivate().getEncoded();
        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] plaintext = "plaintext".getBytes();
        byte[] signature = this.signature.sign(plaintext, new ECParameters(privateKey, publicKey));
        Assertions.assertTrue(
                this.signature.verify(plaintext, signature, new ECParameters(publicKey, false)));
    }
}
