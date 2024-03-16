package io.ghcr.heliannuuthus.devtools.crypto.parameters;

import static io.ghcr.heliannuuthus.devtools.crypto.algorithms.Padding.PKCS7;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.Padding;
import java.security.spec.AlgorithmParameterSpec;
import java.util.random.RandomGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AESParameters extends BlockParameters {

  public AESParameters(byte[] key, String encryptMode) {
    this(key, generateIv(16), PKCS7, encryptMode);
  }

  public AESParameters(byte[] key, Padding padding, String encryptMode) {
    this(key, generateIv(16), padding, encryptMode);
  }

  protected AESParameters(byte[] key, byte[] iv, Padding padding, String encryptMode) {
    this.key = key;
    this.iv = iv;
    this.mode = encryptMode;
    this.padding = padding;
  }

  public static byte[] generateIv(int len) {
    byte[] iv = new byte[len];
    RandomGenerator.getDefault().nextBytes(iv);
    return iv;
  }

  public static final String CBC_MODE = "CBC";
  public static final String GCM_MODE = "GCM";

  private byte[] key;

  private byte[] iv;

  private Padding padding;

  private String mode;

  @Override
  public String getName() {
    return AES_ALGORITHM;
  }

  @Override
  public String getMode() {
    return mode;
  }

  @Override
  public SecretKey getKey() {
    return new SecretKeySpec(this.key, getName());
  }

  @Override
  public AlgorithmParameterSpec getSpec() {
    return new IvParameterSpec(iv);
  }

  @Override
  public Padding getPadding() {
    return this.padding;
  }
}
