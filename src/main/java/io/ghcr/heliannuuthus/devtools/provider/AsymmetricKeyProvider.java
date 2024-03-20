package io.ghcr.heliannuuthus.devtools.provider;

import io.ghcr.heliannuuthus.devtools.exception.CryptoException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Objects;
import org.apache.commons.lang3.tuple.Pair;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

@Component()
public class AsymmetricKeyProvider {

  public static final String ASYMMETRIC = "asymmetric";

  public Pair<byte[], byte[]> generate(KeyGenParameters parameters) {
    try {
      KeyPairGenerator keyPairGenerator =
          KeyPairGenerator.getInstance(parameters.getName(), BouncyCastleProvider.PROVIDER_NAME);
      if (Objects.nonNull(parameters.getSpec())) {
        keyPairGenerator.initialize(parameters.getSpec());
      } else if (parameters.getSize() > -1) {
        keyPairGenerator.initialize(parameters.getSize());
      }
      KeyPair keyPair = keyPairGenerator.generateKeyPair();
      return Pair.of(keyPair.getPrivate().getEncoded(), keyPair.getPublic().getEncoded());
    } catch (Exception e) {
      throw new CryptoException(CryptoException.INIT_MODE, parameters.getName(), e);
    }
  }
}
