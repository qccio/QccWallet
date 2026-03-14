package qcc.wallet;

import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.pqc.crypto.mldsa.*;
import org.bouncycastle.util.encoders.Hex;
import qcc.util.CryptoUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class QccWallet {
    public final String address;
    public String privateKeyHex;
    public final String publicKeyHex;

    public QccWallet(String address, String privateKey, String publicKey) {
        this.address = address;
        this.privateKeyHex = privateKey;
        this.publicKeyHex = publicKey;
    }

    // 获取 ML-DSA 私钥对象
    public MLDSAPrivateKeyParameters getPrivateObj() {
        return new MLDSAPrivateKeyParameters(MLDSAParameters.ml_dsa_87, Hex.decode(privateKeyHex));
    }

    // 获取 ML-DSA 公钥对象
    public MLDSAPublicKeyParameters getPubObj() {
        return new MLDSAPublicKeyParameters(MLDSAParameters.ml_dsa_87, Hex.decode(publicKeyHex));
    }

    // 签名字符串
    public String signString(String message) throws CryptoException {
        byte[] data = message.getBytes(StandardCharsets.UTF_8);
        return Hex.toHexString(signByte(data));
    }

    // 签名原始字节 (ML-DSA 采用 update 模式)
    public byte[] signByte(byte[] data) throws CryptoException {
        MLDSASigner signer = new MLDSASigner();
        signer.init(true, getPrivateObj());
        signer.update(data, 0, data.length);
        return signer.generateSignature();
    }

    // 验证字符串
    public boolean verifyString(String message, String signatureHex) {
        return verifyByte(message.getBytes(StandardCharsets.UTF_8), Hex.decode(signatureHex));
    }

    // 验证字节流
    public boolean verifyByte(byte[] message, byte[] signature) {
        MLDSASigner verifier = new MLDSASigner();
        verifier.init(false, getPubObj());
        verifier.update(message, 0, message.length);
        return verifier.verifySignature(signature);
    }



    public void destroy() {
        if (privateKeyHex != null) {
            privateKeyHex = "";
        }
        System.gc();
    }

    public String toHash() {
        String data = address + privateKeyHex + publicKeyHex;
        try {
            return CryptoUtil.bytesToHex(CryptoUtil.sha3256(data.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}