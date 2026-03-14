package qcc.wallet;

import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.pqc.crypto.mldsa.*;
import org.bouncycastle.util.encoders.Hex;
import qcc.util.LogObj;

import java.security.SecureRandom;
import java.util.List;

/**
 * QCC 助记词钱包生成器 (ML-DSA-87 标准版)
 */
public class QccMnemonicWallet {

    /**
     * 根据助记词和密码生成确定性的 ML-DSA-87 钱包
     * @param mnemonic 助记词
     * @param passphrase 支付密码/盐
     */
    public static QccWallet fromMnemonic(String mnemonic, String passphrase) {
        try {
            // 1. 解析助记词并生成 BIP39 种子 (512bit)
            List<String> words = List.of(mnemonic.split(" "));
            byte[] seed = MnemonicCode.toSeed(words, passphrase);

            // 2. 取前 32 字节作为 ML-DSA 的确定性种子
            // ML-DSA 生成过程高度依赖于种子，确保这里的一致性
            byte[] mldsaSeed = new byte[32];
            System.arraycopy(seed, 0, mldsaSeed, 0, 32);

            // 3. 初始化 ML-DSA 生成器
            MLDSAKeyPairGenerator generator = new MLDSAKeyPairGenerator();

            // 使用自定义的确定性随机源
            SecureRandom deterministicRandom = new DeterministicRandom(mldsaSeed);
            generator.init(new MLDSAKeyGenerationParameters(
                    deterministicRandom,
                    MLDSAParameters.ml_dsa_87
            ));

            // 4. 生成密钥对
            AsymmetricCipherKeyPair kp = generator.generateKeyPair();
            MLDSAPublicKeyParameters pubKey = (MLDSAPublicKeyParameters) kp.getPublic();
            MLDSAPrivateKeyParameters privKey = (MLDSAPrivateKeyParameters) kp.getPrivate();

            // 5. 编码转换 (ML-DSA 使用标准 getEncoded 即可)
            String privHex = Hex.toHexString(privKey.getEncoded());
            String pubHex = Hex.toHexString(pubKey.getEncoded());

            // 6. 调用之前的工具类生成地址
            String address = AddressTool.creatAddress(pubHex);

            LogObj.println("ML-DSA-87 助记词钱包恢复成功！");
            LogObj.println("地址: " + address);

            return new QccWallet(address, privHex, pubHex);

        } catch (Exception e) {
            throw new RuntimeException("助记词恢复钱包失败", e);
        }
    }

    /**
     * 确定性随机数辅助类
     * 确保相同的 seed 产生相同的 byte 序列，供生成器使用
     */
    private static class DeterministicRandom extends SecureRandom {
        private final byte[] seed;
        private int pointer = 0;

        public DeterministicRandom(byte[] seed) {
            this.seed = seed;
        }

        @Override
        public void nextBytes(byte[] bytes) {
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = seed[pointer % seed.length];
                pointer++;
            }
        }
    }

    /**
     * 随机生成 24 个助记词
     */
    public static String generateRandomMnemonic() {
        try {
            SecureRandom random = new SecureRandom();
            byte[] entropy = new byte[32]; // 256bit 熵 -> 24个单词
            random.nextBytes(entropy);
            List<String> words = MnemonicCode.INSTANCE.toMnemonic(entropy);
            return String.join(" ", words);
        } catch (MnemonicException.MnemonicLengthException e) {
            throw new RuntimeException("生成助记词失败", e);
        }
    }

    public static void main(String[] args) {
        // 测试生成
        String myMnemonic = generateRandomMnemonic();
        String payPassword = "user_secure_password_123";

        LogObj.println("生成的助记词: " + myMnemonic);
        LogObj.println("支付密码: " + payPassword);

        QccWallet wallet = fromMnemonic(myMnemonic, payPassword);
        System.out.println("恢复出的地址: " + wallet.address);
    }
}