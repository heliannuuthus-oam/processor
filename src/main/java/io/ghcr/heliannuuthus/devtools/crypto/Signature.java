package io.ghcr.heliannuuthus.devtools.crypto;

import io.ghcr.heliannuuthus.devtools.crypto.parameters.AsymmetricParameters;
import io.ghcr.heliannuuthus.devtools.exception.CryptoException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

@Component
public class Signature {
  public byte[] sign(byte[] plaintext, AsymmetricParameters parameters) {
    try {
      java.security.Signature signer =
          java.security.Signature.getInstance(
              parameters.getAlgorithm(), BouncyCastleProvider.PROVIDER_NAME);
      signer.initSign(parameters.getPrivateKey());
      signer.update(plaintext);
      return signer.sign();
    } catch (Exception e) {
      throw new CryptoException(CryptoException.SIGN_MODE, parameters.getName(), e);
    }
  }

  public boolean verify(byte[] plaintext, byte[] signature, AsymmetricParameters parameters) {
    try {
      java.security.Signature signer =
          java.security.Signature.getInstance(
              parameters.getAlgorithm(), BouncyCastleProvider.PROVIDER_NAME);
      signer.initVerify(parameters.getPublicKey());
      signer.update(plaintext);
      return signer.verify(signature);
    } catch (Exception e) {
      throw new CryptoException(CryptoException.VERIFY_MODE, parameters.getName(), e);
    }
  }
}
