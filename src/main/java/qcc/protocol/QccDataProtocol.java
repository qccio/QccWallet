package qcc.protocol;

import org.bouncycastle.pqc.crypto.crystals.dilithium.DilithiumParameters;
import org.bouncycastle.pqc.crypto.crystals.dilithium.DilithiumPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.crystals.dilithium.DilithiumPublicKeyParameters;
import qcc.core.qccData.QueBaseData;
import qcc.core.qccData.QubeDataBaseFactory;
import org.bouncycastle.util.encoders.Hex;
import qcc.wallet.AddressTool;
import qcc.wallet.WalletSigner;

import java.security.SecureRandom;

/**
 * 新版 QubeChain 通讯协议（客户端<->节点）v2
 *
 * 作用：
 *     QueBaseData  <->  QubePacket
 *
 * 特点：
 *     ✔ 不再依赖 QueBaseData 中的签名字段
 *     ✔ 不再传递 rawPk
 *     ✔ publicKeyHex 是 DER 格式，兼容所有 Dilithium 实现
 *     ✔ 签名只针对 payloadJson
 *     ✔ address 由服务器端根据 rawPk 自动生成
 */
public class QccDataProtocol {

    private static final SecureRandom RANDOM = new SecureRandom();

    // ================================================================
    //  1. 客户端编码：QueBaseData -> QubePacket
    // ================================================================
    public static QccPacket encodeData(
            QueBaseData data,
            DilithiumPrivateKeyParameters privKey,
            String publicKeyHex
    ) {

        // -------- 1. JSON 序列化业务数据 --------
        String payloadJson = QubeDataBaseFactory.toJson(data);

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
    //  2. 服务端解码：QubePacket -> QueBaseData（自动验签）
    // ================================================================
    public static QueBaseData decodeData(QccPacket packet) {

        try {
            // -------- 1. 解析 DER 公钥 --------
            byte[] pubBytes = Hex.decode(packet.publicKey);
            DilithiumPublicKeyParameters pubKey =
                    new DilithiumPublicKeyParameters(DilithiumParameters.dilithium5, pubBytes);
            // -------- 2. 验证签名 --------
            boolean ok = WalletSigner.verify(
                    packet.payloadJson,
                    packet.signature,
                    pubKey
            );

            if (!ok) {
                throw new IllegalStateException("签名验证失败");
            }

            // -------- 3. 解析 payloadJson（自动识别三大数据类型） --------
            QueBaseData data = QubeDataBaseFactory.fromJson(packet.payloadJson);
            // -------- 4. 自动推导 from 地址 --------
           // byte[] rawPk = WalletSigner.getRawPublicKey(pubKey);
            String address = AddressTool.creatAddress(packet.publicKey);
            data.from = address;   // 强制覆盖 data.from

            return data;

        } catch (Exception e) {
            throw new IllegalStateException("QubeDataProtocol decodeData 失败: " + e.getMessage(), e);
        }
    }
}
