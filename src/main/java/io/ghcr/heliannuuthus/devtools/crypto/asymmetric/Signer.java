package io.ghcr.heliannuuthus.devtools.crypto.asymmetric;

import java.security.NoSuchAlgorithmException;
import java.security.Signature;

public class Signer {

    public byte[] sign(byte[] plaintext) throws NoSuchAlgorithmException {
        try {
            Signature signer = Signature.getInstance("EdDSA");
            signer.initSign();
            signer.update(plaintext);
            return signer.sign();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean verify(byte[] plaintext, byte[] signature) throws NoSuchAlgorithmException {
        try {
            Signature signer = Signature.getInstance("EdDSA");
            signer.initVerify();
            signer.update(plaintext);
            return signer.verify(signature);
        } catch (Exception e) {
            return false;
        }
    }
}
