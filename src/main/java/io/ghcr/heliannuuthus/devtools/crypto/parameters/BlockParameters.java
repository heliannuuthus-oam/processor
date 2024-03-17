package io.ghcr.heliannuuthus.devtools.crypto.parameters;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.Padding;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
@Getter
public abstract class BlockParameters implements OamParameters {

  public static final String ECB_MODE = "ECB";
  public static final String CBC_MODE = "CBC";
  public static final String GCM_MODE = "GCM";

  protected byte[] key;

  @Override
  public String getAlgorithm() {
    return StringUtils.joinWith("/", getName(), getMode(), getPadding().getName());
  }

  public String getName() {
    return null;
  }

  public abstract String getMode();

  public Padding getPadding() {
    return Padding.PKCS7;
  }

  public SecretKey getKey() {
    return new SecretKeySpec(this.key, getName());
  }

  public AlgorithmParameterSpec getSpec() {
    return null;
  }
}
