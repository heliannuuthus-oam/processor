package io.ghcr.heliannuuthus.devtools.crypto.parameters;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.OamAlgorithm;
import io.ghcr.heliannuuthus.devtools.crypto.algorithms.Padding;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;
import org.apache.commons.lang3.StringUtils;

public abstract class BlockParameters implements OamAlgorithm {

  @Override
  public String getAlgorithm() {
    return StringUtils.joinWith("/", getName(), getMode(), getPadding().getName());
  }

  public abstract String getName();

  public abstract String getMode();

  public Padding getPadding() {
    return Padding.PKCS7;
  }

  public abstract SecretKey getKey();

  public abstract AlgorithmParameterSpec getSpec();
}
