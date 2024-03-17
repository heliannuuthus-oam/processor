package io.ghcr.heliannuuthus.devtools.crypto.parameters.aes;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.Padding;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.jcajce.spec.AEADParameterSpec;

import java.security.spec.AlgorithmParameterSpec;

import static io.ghcr.heliannuuthus.devtools.utils.CryptoUtils.nextBytes;


@Getter
@Setter
public class AESGCMParameters extends AESCBCParameters {

    private byte[] aad;

    public AESGCMParameters(byte[] key) {
        this(key, nextBytes(12));
    }

    public AESGCMParameters(byte[] key, byte[] iv) {
        this(key, iv, null);
    }

    public AESGCMParameters(byte[] key, byte[] iv, byte[] aad) {
        super(key, iv, Padding.None);
        this.aad = aad;
    }

    @Override
    public String getMode() {
        return GCM_MODE;
    }

    @Override
    public AlgorithmParameterSpec getSpec() {
        return new AEADParameterSpec(getIv(), 128, aad);
    }
}
