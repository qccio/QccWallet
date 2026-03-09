package qcc.wallet;


import org.bouncycastle.pqc.crypto.crystals.dilithium.DilithiumPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.crystals.dilithium.DilithiumPublicKeyParameters;
import org.bouncycastle.pqc.crypto.crystals.dilithium.DilithiumSigner;
import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class WalletSigner {

    //===============================
    //   String 消息 → HEX 签名
    //===============================
    public static String sign(String msg, DilithiumPrivateKeyParameters priv) {
        return sign(msg.getBytes(StandardCharsets.UTF_8), priv);
    }

    //===============================
    //   byte[] 消息 → HEX 签名
    //===============================
    public static String sign(byte[] msgBytes, DilithiumPrivateKeyParameters priv) {
        DilithiumSigner signer = new DilithiumSigner();
        signer.init(true, priv);
        byte[] sig = signer.generateSignature(msgBytes);
        return Hex.toHexString(sig);
    }

    //===============================
    //   String 消息 + 签名(HEX) 验证
    //===============================
    public static boolean verify(String msg, String sigHex, DilithiumPublicKeyParameters pub) {
        return verify(msg.getBytes(StandardCharsets.UTF_8), Hex.decode(sigHex), pub);
    }

    //===============================
    //   byte[] 消息 + 签名(byte) 验证
    //===============================
    public static boolean verify(byte[] msgBytes, byte[] sigBytes, DilithiumPublicKeyParameters pub) {
        DilithiumSigner verifier = new DilithiumSigner();
        verifier.init(false, pub);
        return verifier.verifySignature(msgBytes, sigBytes);
    }


    // ====================2. 原始公钥（rho + t1） ====================
    public static byte[] getRawPublicKey(DilithiumPublicKeyParameters pub) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            baos.write(pub.getRho());   // 32 bytes
            baos.write(pub.getT1());    // 2560 bytes
            return baos.toByteArray();  // 2592 bytes
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    // ====================3. 抽取原始私钥（七段合并） ====================
    public static byte[] getRawPrivateKey(DilithiumPrivateKeyParameters priv) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            baos.write(priv.getRho());
            baos.write(priv.getK());
            baos.write(priv.getTr());
            baos.write(priv.getS1());
            baos.write(priv.getS2());
            baos.write(priv.getT0());
            baos.write(priv.getT1());
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
