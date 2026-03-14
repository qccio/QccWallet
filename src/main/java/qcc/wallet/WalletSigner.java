package qcc.wallet;

import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.pqc.crypto.mldsa.*;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;

/**
 * QCC ML-DSA-87 签名与验证工具
 */
public class WalletSigner {

    //===============================
    //   String 消息 → HEX 签名
    //===============================
    public static String sign(String msg, MLDSAPrivateKeyParameters priv) throws CryptoException {
        return sign(msg.getBytes(StandardCharsets.UTF_8), priv);
    }

    /**
     * byte[] 消息 → HEX 签名
     * 注意：MLDSASigner 必须调用 update 注入数据
     */
    public static String sign(byte[] msgBytes, MLDSAPrivateKeyParameters priv) throws CryptoException {
        MLDSASigner signer = new MLDSASigner();
        signer.init(true, priv); // true = 签名模式
        signer.update(msgBytes, 0, msgBytes.length); // 注入原始消息
        byte[] sig = signer.generateSignature(); // 生成签名
        return Hex.toHexString(sig);
    }

    public static boolean verifyFlutterSignature(String pubKeyHex, String payload, String sigHex) {
        try {
            byte[] publicKeyBytes = Hex.decode(pubKeyHex);
            byte[] signatureBytes = Hex.decode(sigHex);
            byte[] messageBytes = payload.getBytes(StandardCharsets.UTF_8);
            // 构造 ML-DSA-87 参数 (对应 Dilithium 5 的 FIPS 版本)
            MLDSAPublicKeyParameters pubKeyParams = new MLDSAPublicKeyParameters(
                    MLDSAParameters.ml_dsa_87,
                    publicKeyBytes
            );
            MLDSASigner verifier = new MLDSASigner();
            // 默认为空 Context，与 Flutter liboqs 默认配置对齐
            // 2. 初始化验证器
            verifier.init(false, pubKeyParams);
            // 3. 【关键修正】：必须先调用 update 注入原始消息
            // 这是由于 MLDSASigner 实现了 Signer 接口，采用了流式处理
            verifier.update(messageBytes, 0, messageBytes.length);
            // 4. 最后传入签名进行验证
            boolean result = verifier.verifySignature(signatureBytes);
            // System.out.println("--------------------------------------");
            // System.out.println("【收到签名验证请求】");
            // System.out.println("消息内容: " + payload);
            // System.out.println("签名长度: " + signatureBytes.length);
            System.out.println("验证状态: " + (result ? "✅ 通过" : "❌ 拦截"));
            // System.out.println("--------------------------------------");
            return result;
        } catch (Exception e) {
            System.err.println("验签逻辑崩溃: " + e.getMessage());
            return false;
        }
    }

    /**
     * byte[] 消息 + 签名(byte) 验证
     * 必须先 update 消息内容，再 verifySignature
     */
    public static boolean verify(byte[] msgBytes, byte[] sigBytes, MLDSAPublicKeyParameters pub) {
        MLDSASigner verifier = new MLDSASigner();
        verifier.init(false, pub); // false = 验证模式
        verifier.update(msgBytes, 0, msgBytes.length); // 注入验证的消息
        return verifier.verifySignature(sigBytes); // 验证签名
    }

    // ==================== 2. 获取原始公钥字节 ====================
    /**
     * ML-DSA-87 公钥编码符合 FIPS 204 标准
     * 长度固定为 2592 字节
     */
    public static byte[] getRawPublicKey(MLDSAPublicKeyParameters pub) {
        return pub.getEncoded();
    }

    // ==================== 3. 获取原始私钥字节 ====================
    /**
     * ML-DSA-87 私钥编码符合 FIPS 204 标准
     * 包含种子和秘密向量，长度固定为 4896 字节
     */
    public static byte[] getRawPrivateKey(MLDSAPrivateKeyParameters priv) {
        return priv.getEncoded();
    }
}