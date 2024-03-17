package io.ghcr.heliannuuthus.devtools.crypto.parameters;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.OamAlgorithm;
import io.ghcr.heliannuuthus.devtools.exception.CryptoException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;

@Getter
@NoArgsConstructor
public abstract class EdDSAParameters implements OamAlgorithm {

    public EdDSAParameters(byte[] key, boolean isPrivate) throws NoSuchAlgorithmException {
        this();
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(this.getName());
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
            if (isPrivate) {
                this.privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            } else {
                this.publicKey = keyFactory.generatePublic(pkcs8EncodedKeySpec);
            }
        } catch (Exception e) {
            throw new CryptoException(-1, getName());
        }
    }

    public EdDSAParameters(byte[] privateKey, byte[] publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(this.getName());
            this.privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
            this.publicKey = keyFactory.generatePublic(new PKCS8EncodedKeySpec(publicKey));
        } catch (Exception e) {
            throw new CryptoException(-1, getName());
        }
    }

    @Override
    public String getAlgorithm() {
        return ED_DSA_ALGORITHM;
    }

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public abstract String getName();

    public AlgorithmParameterSpec getSpec() {
        return new EdDSAParameterSpec(getName());
    }
}
