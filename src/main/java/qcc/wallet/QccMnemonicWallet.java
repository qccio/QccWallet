package qcc.wallet;

import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.pqc.crypto.crystals.dilithium.*;
import org.bouncycastle.util.encoders.Hex;
import qcc.util.LogObj;

import java.security.SecureRandom;
import java.util.List;

import static qcc.wallet.WalletUtil.getRawPrivateKeyBytes;

/**
 * QCC 助记词钱包生成器
 */
public class QccMnemonicWallet {
    /**
     * 根据 24 个助记词生成确定性的 Dilithium5 钱包
     * @param mnemonic 24个单词
     * @param passphrase 可选密码（盐），没有传空字符串
     */
    public static QccWallet fromMnemonic(String mnemonic, String passphrase) {
        try {
            // 1. 解析助记词
            List<String> words = List.of(mnemonic.split(" "));
            // 2. 使用 BIP39 标准派生 512bit seed
            byte[] seed = MnemonicCode.toSeed(words, passphrase);
            // 3. 取前 32 字节作为 Dilithium 的确定性种子
            byte[] dilithiumSeed = new byte[32];
            System.arraycopy(seed, 0, dilithiumSeed, 0, 32);
            // 4. 初始化 Dilithium
            DilithiumKeyPairGenerator generator = new DilithiumKeyPairGenerator();
            SecureRandom deterministicRandom = new DeterministicRandom(dilithiumSeed);
            generator.init(new DilithiumKeyGenerationParameters(
                    deterministicRandom,
                    DilithiumParameters.dilithium5
            ));

            AsymmetricCipherKeyPair kp = generator.generateKeyPair();

            DilithiumPublicKeyParameters pubKey = (DilithiumPublicKeyParameters) kp.getPublic();
            DilithiumPrivateKeyParameters privKey = (DilithiumPrivateKeyParameters) kp.getPrivate();

            String privHex= Hex.toHexString(getRawPrivateKeyBytes(privKey));  //私钥转换方式，千万不要修改，目前测试 ok
            String pubHex = Hex.toHexString(pubKey.getEncoded());

            String address = AddressTool.creatAddress(pubHex);
            LogObj.println("抗量子钱包生成成功！");
            LogObj.println("公钥匙长度: " + pubHex.length() + ")字节长度:"+pubKey.getEncoded().length);
            LogObj.println("地址: " + address + " (长度: " + address.length() + ")");
            LogObj.println("私钥长度: " + privHex.length() + "... (测试阶段打印，上线必须加密私钥长度:)"+ privKey.getEncoded().length);

            return new QccWallet(address, privHex, pubHex);

        } catch (Exception e) {
            throw new RuntimeException("助记词恢复钱包失败", e);
        }
    }


    /**
     * 确定性随机数辅助类
     */
    private static class DeterministicRandom extends SecureRandom {
        private final byte[] seed;
        private int pointer = 0;

        public DeterministicRandom(byte[] seed) {
            this.seed = seed;
        }

        @Override
        public void nextBytes(byte[] bytes) {
            // Dilithium 生成密钥时需要从随机源获取字节
            // 我们通过 SHA-3 或简单的种子循环保证其确定性
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = seed[pointer % seed.length];
                pointer++;
            }
        }
    }


    public static void main(String[] args) {
        String myMnemonic = generateRandomMnemonic();
        LogObj.println("随机生成24个单词，作为助记词："+myMnemonic);
        QccWallet wallet = fromMnemonic(myMnemonic, "");
        System.out.println("QCC Address: " + wallet.address);
        System.out.println("Public Key: " + wallet.publicKeyHex.substring(0, 30) + "...");
    }


    public static QccWallet  creatqcc() {
        String myMnemonic = generateRandomMnemonic();
        LogObj.println("随机生成24个单词，作为助记词："+myMnemonic);
        QccWallet wallet = fromMnemonic(myMnemonic, "123456");
        LogObj.println("public key info:"+wallet.publicKeyHex);
        return wallet;
    }



    /**
     * 随机生成 24 个助记词
     */
    public static String generateRandomMnemonic() {
        try {
            SecureRandom random = new SecureRandom();

            // 生成 256bit 熵（对应 24 个单词）
            byte[] entropy = new byte[32];
            random.nextBytes(entropy);

            List<String> words = MnemonicCode.INSTANCE.toMnemonic(entropy);

            return String.join(" ", words);

        } catch (MnemonicException.MnemonicLengthException e) {
            throw new RuntimeException("生成助记词失败", e);
        }
    }

}
