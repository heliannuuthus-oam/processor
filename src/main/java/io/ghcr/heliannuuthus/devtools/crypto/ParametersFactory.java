package io.ghcr.heliannuuthus.devtools.crypto;

import static io.ghcr.heliannuuthus.devtools.crypto.parameters.OamParameters.*;

import com.google.common.collect.Sets;
import io.ghcr.heliannuuthus.devtools.crypto.algorithms.MessageDigest;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.SignParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.ecdsa.ECCParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.ecdsa.ECIESParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.eddsa.Ed25519Parameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.eddsa.Ed448Parameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.rsa.RSAParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.rsa.RSAStreamParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.sm2.SM2Parameters;
import io.ghcr.heliannuuthus.devtools.exception.BadRequestException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;

public class ParametersFactory {

  private static final ParametersFactory INSTANCE = new ParametersFactory();
  private static final Set<String> RSA =
      Stream.of(MessageDigest.values())
          .map(md -> md + CONNECTOR + RSA_ALGORITHM)
          .collect(Collectors.toSet());
  private static final Set<String> ECC =
      Stream.of(MessageDigest.values())
          .map(md -> md + CONNECTOR + ECDSA_ALGORITHM)
          .collect(Collectors.toSet());
  private static final Set<String> ED =
      Sets.newHashSet(EdDSAParameterSpec.Ed448, EdDSAParameterSpec.Ed25519);
  private static final Set<String> GM =
      Stream.of(MessageDigest.SM3, MessageDigest.SHA_256)
          .map(md -> md + CONNECTOR + SM2_ALGORITHM)
          .collect(Collectors.toSet());

  public static ParametersFactory getInstance() {
    return INSTANCE;
  }

  public SignParameters createForSign(String algorithm, byte[] key, boolean forSign) {
    if (RSA.contains(algorithm)) {
      return new RSAParameters(key, forSign);
    } else if (ECC.contains(algorithm)) {
      return new ECCParameters(key, forSign);
    } else if (ED.contains(algorithm)) {
      switch (algorithm) {
        case EdDSAParameterSpec.Ed448 -> new Ed448Parameters(key, forSign);
        case EdDSAParameterSpec.Ed25519 -> new Ed25519Parameters(key, forSign);
        default -> throw new BadRequestException("unsupported ed algorithm " + algorithm);
      }

    } else if (GM.contains(algorithm)) {
      return new SM2Parameters(key, forSign);
    }
    throw new BadRequestException("unsupported stream sign algorithm " + algorithm);
  }

  public SignParameters createForEncrypt(String algorithm, byte[] key, boolean forEncrypt) {
    if (RSA.contains(algorithm)) {
      return new RSAStreamParameters(key, !forEncrypt);
    } else if (ECC.contains(algorithm)) {
      return new ECIESParameters(key, !forEncrypt);
    }
    throw new BadRequestException("unsupported stream encrypt algorithm " + algorithm);
  }
}
