package io.ghcr.heliannuuthus.devtools.crypto.parameters;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.MessageDigest;
import io.ghcr.heliannuuthus.devtools.exception.CryptoException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import lombok.Getter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

@Getter
public abstract class AsymmetricParameters implements OamParameters {

  private MessageDigest messageDigest = MessageDigest.SHA_256;

  protected AsymmetricParameters() {}

  public AsymmetricParameters(byte[] key, boolean isPrivate) {
    this();
    try {
      KeyFactory keyFactory =
          KeyFactory.getInstance(this.getName(), BouncyCastleProvider.PROVIDER_NAME);
      if (isPrivate) {
        this.privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(key));
      } else {
        this.publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(key));
      }
    } catch (Exception e) {
      throw new CryptoException(-1, getName(), e);
    }
  }

  public AsymmetricParameters(byte[] privateKey, byte[] publicKey) {
    try {
      KeyFactory keyFactory =
          KeyFactory.getInstance(this.getName(), BouncyCastleProvider.PROVIDER_NAME);
      this.privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
      this.publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
    } catch (Exception e) {
      throw new CryptoException(-1, getName(), e);
    }
  }

  private PrivateKey privateKey;
  private PublicKey publicKey;

  public abstract String getName();

  public String getMessageDigest() {
    return messageDigest.getName();
  }

  public AsymmetricParameters md(MessageDigest messageDigest) {
    this.messageDigest = messageDigest;
    return this;
  }

  @Override
  public String getAlgorithm() {
    return getMessageDigest() + "with" + getName();
  }
}
