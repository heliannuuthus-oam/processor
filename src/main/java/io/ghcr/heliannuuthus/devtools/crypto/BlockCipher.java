package io.ghcr.heliannuuthus.devtools.crypto;

import static io.ghcr.heliannuuthus.devtools.exception.CryptoException.DECRYPT_MODE;
import static io.ghcr.heliannuuthus.devtools.exception.CryptoException.ENCRYPT_MODE;

import io.ghcr.heliannuuthus.devtools.crypto.parameters.BlockParameters;
import io.ghcr.heliannuuthus.devtools.exception.CryptoException;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

@Component
public class BlockCipher {
  public byte[] encrypt(byte[] plaintext, BlockParameters parameters) {
    try {
      Cipher cipher =
          Cipher.getInstance(parameters.getAlgorithm(), BouncyCastleProvider.PROVIDER_NAME);
      cipher.init(Cipher.ENCRYPT_MODE, parameters.getKey(), parameters.getSpec());
      return cipher.doFinal(plaintext);
    } catch (Exception e) {
      throw new CryptoException(ENCRYPT_MODE, parameters.getAlgorithm(), e);
    }
  }

  public byte[] decrypt(byte[] cypher, BlockParameters parameters) {
    try {
      Cipher cipher =
          Cipher.getInstance(parameters.getAlgorithm(), BouncyCastleProvider.PROVIDER_NAME);
      cipher.init(Cipher.DECRYPT_MODE, parameters.getKey(), parameters.getSpec());
      return cipher.doFinal(cypher);
    } catch (Exception e) {
      throw new CryptoException(DECRYPT_MODE, parameters.getAlgorithm(), e);
    }
  }
}
