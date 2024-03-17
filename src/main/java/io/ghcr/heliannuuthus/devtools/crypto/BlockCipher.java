package io.ghcr.heliannuuthus.devtools.crypto;

import io.ghcr.heliannuuthus.devtools.crypto.parameters.BlockParameters;
import io.ghcr.heliannuuthus.devtools.exception.CryptoException;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class BlockCipher {
  public byte[] encrypt(byte[] plaintext, BlockParameters parameters) {
    try {
      Cipher cipher =
          Cipher.getInstance(parameters.getAlgorithm(), BouncyCastleProvider.PROVIDER_NAME);
      cipher.init(Cipher.ENCRYPT_MODE, parameters.getKey(), parameters.getSpec());
      return cipher.doFinal(plaintext);
    } catch (Exception e) {
      throw new CryptoException(Cipher.ENCRYPT_MODE, parameters.getAlgorithm(), e);
    }
  }

  public byte[] decrypt(byte[] cypher, BlockParameters parameters) {
    try {
      Cipher cipher =
          Cipher.getInstance(parameters.getAlgorithm(), BouncyCastleProvider.PROVIDER_NAME);
      cipher.init(Cipher.DECRYPT_MODE, parameters.getKey(), parameters.getSpec());
      return cipher.doFinal(cypher);
    } catch (Exception e) {
      throw new CryptoException(Cipher.DECRYPT_MODE, parameters.getAlgorithm(), e);
    }
  }
}
