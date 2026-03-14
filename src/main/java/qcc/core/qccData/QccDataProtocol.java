package qcc.core.qccData;

import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAPrivateKeyParameters;
import qcc.wallet.WalletSigner;
import java.security.SecureRandom;

/**
 * 新版 QccChain 通讯协议（客户端<->节点）v2
 *
 * 作用：
 *     QccBaseData  <->  QubePacket
 *
 * 特点：
 *     ✔ 不再依赖 QccBaseData 中的签名字段
 *     ✔ 不再传递 rawPk
 *     ✔ publicKeyHex 是 DER 格式，兼容所有 Dilithium 实现
 *     ✔ 签名只针对 payloadJson
 *     ✔ address 由服务器端根据 rawPk 自动生成
 */
public class QccDataProtocol {

    private static final SecureRandom RANDOM = new SecureRandom();

    // ================================================================
    //  1. 客户端编码：QccBaseData -> QccPacket
    // ================================================================
    public static QccPacket encodeData(
            QccBaseData data,
            MLDSAPrivateKeyParameters privKey,
            String publicKeyHex
    ) throws CryptoException {
        // -------- 1. JSON 序列化业务数据 --------
        String payloadJson = QccDataBaseFactory.toJson(data);
        // -------- 2. Dilithium 签名 --------
        String signature = WalletSigner.sign(payloadJson, privKey);
        // -------- 3. 封装传输包 --------
        QccPacket packet = new QccPacket();
        packet.payloadJson = payloadJson;
        packet.signature = signature;
        packet.publicKey = publicKeyHex;
        packet.clientTime = System.currentTimeMillis();
        packet.nonce = RANDOM.nextLong();
       // LogObj.println("公钥匙长度"+publicKeyHex.length());
        return packet;
    }


    // ================================================================
    //  1. 客户端编码：QccBaseData -> QubePacket
    // ================================================================
    public static QccPacket encodeEntityData(
            EntityData data,
            MLDSAPrivateKeyParameters privKey,
            String publicKeyHex
    ) throws CryptoException {
        // -------- 1. JSON 序列化业务数据 --------
       // String payloadJson = QccDataBaseFactory.toJson(data);

        // -------- 2. Dilithium 签名 --------
        String signature = WalletSigner.sign(data.getPayloadForHash(), privKey);
        // -------- 3. 封装传输包 --------
        QccPacket packet = new QccPacket();
        packet.payloadJson = data.toJson();
        packet.signature = signature;
        packet.publicKey = publicKeyHex;
        packet.clientTime = System.currentTimeMillis();
        packet.nonce = RANDOM.nextLong();
        // LogObj.println("公钥匙长度"+publicKeyHex.length());
        return packet;
    }



}
