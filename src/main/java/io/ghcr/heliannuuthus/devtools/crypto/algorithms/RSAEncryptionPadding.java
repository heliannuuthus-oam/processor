package io.ghcr.heliannuuthus.devtools.crypto.algorithms;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RSAEncryptionPadding {
  OAEP_SHA1("OAEPWithSHA-1AndMGF1Padding", MessageDigest.SHA_1),
  OAEP_SHA256("OAEPWithSHA-256AndMGF1Padding", MessageDigest.SHA_256),
  OAEP_SHA3_256("OAEPWithSHA3-256AndMGF1Padding", MessageDigest.SHA3_256),
  OAEP_SHA3_384("OAEPWithSHA3-384AndMGF1Padding", MessageDigest.SHA3_384),
  OAEP_SHA3_512("OAEPWithSHA3-512AndMGF1Padding", MessageDigest.SHA3_512),
  OAEP_SHA384("OAEPWithSHA-384AndMGF1Padding", MessageDigest.SHA_384),
  OAEP_SHA512("OAEPWithSHA-512AndMGF1Padding", MessageDigest.SHA_512),
  PKCSV1_5("PKCS1Padding", null);
  private final String value;
  private final MessageDigest md;
}
