package qcc;   // 你现在的包名保持不变

import com.alibaba.fastjson2.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import qcc.config.QCCPath;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;   // 这行必须加！开启定时任务
import qcc.core.qccData.TransactionData;
import qcc.protocol.QccDataProtocol;
import qcc.util.LogObj;
import qcc.util.ToolsUtil;
import qcc.wallet.QccWallet;

import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.time.LocalDateTime;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// 这三个注解是核心！缺一不可
@SpringBootApplication        // 告诉IntelliJ这是SpringBoot主类
@EnableScheduling             // 开启10分钟切容器定时器
public class QCCwalletApplication {

    // 【关键修改：使用静态块确保最早注册】
    static {
        // 尝试注册 BouncyCastleProvider
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
            System.out.println(">>> [Static Init] BouncyCastle Provider 已全局注册。");
        }
        // 注意：请确认您使用的是 BouncyCastleProvider.PROVIDER_NAME
        // 或者直接使用 "BC" 字符串，但 BouncyCastleProvider.PROVIDER_NAME 更安全。
    }

    public static void main(String[] args) throws Exception {
        QCCPath.init(); // 就这一行！！！所有路径搞定！！！
       // SpringApplication.run(QubeChainApplication.class, args);
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║    QCC-Chain ∞ 后量子时代              ║");
        System.out.println("║  4秒切容器 | 树状无限扩散 | 抗量子       ║");
        System.out.println("║   7台服务器准备就绪，qcc 上链开始！       ║");
        System.out.println("╚══════════════════════════════════════╝");
       /* TransactionData data = new TransactionData();
        data.id = ToolsUtil.getUUID();
        data.from = "Q1CmbMJQBEt1ugUeGUa3NT8HtmcGgpD9zmE";
        data.to = "Q1CmbMJQBEt1ugUeGUa3NT8HtmcGgpD9zxs";
        data.amount = 100000000L;
        data.assetId = "QCC";
        data.txType = "pay";
        LogObj.println("交易订单json:"+data.toJson());*/
        try {
       /* List<QccWallet> arr=loadGenesisWallets();
        if(arr.isEmpty()) return;
        sendTransactionAsync(arr.getFirst(),arr.getLast(),1000,"QCC");


        String jsonstr="{\"clientTime\":1769326583821,\"nonce\":-6246300583339660109,\"payloadJson\":\"{\\\"amount\\\":100000000,\\\"assetId\\\":\\\"QCC\\\",\\\"from\\\":\\\"Q12CcjQF8dpAinmdcF64ck4Hzoja5vskmWm\\\",\\\"id\\\":\\\"294aecdf6e50443aadef7b4b8f716130\\\",\\\"to\\\":\\\"Q1CmbMJQBEt1ugUeGUa3NT8HtmcGgpD9zxs\\\",\\\"txType\\\":\\\"pay\\\"}\",\"signature\":\"5e39dd3e41a1fd6e2b04128f5dc04bbdbef5405427aea83795ceb83ccc4c5a33ff395804f1d5391f5753a8cef15781045e225440649f06d72b079e7cdfc93e901ec1a05779572a201d286c82205420b1392d3e3eb94557a2c48db28fbf0ec86c3e26565a850bea30ee92d7ded346eefb9feb3afec557a1b3109f9c2b5cb48b7b2229733a23d8de3bdff9fd64ef0bb78cba8f08715805033b5e32daf208e954d376c294f66c27a425b4e357b668dde5cd246dcc018c3393f9b55a85e8dc6597a5f14f65eee85bed05b454c846af953020d242ea55c34c96ec4b8351224bbfc10381fff2ebab96f8d0b4de660e018a3b0ce19b6748e934238b5d432172481864aecdcdd83955744f944f89efe6a867c74060a5a71b7365c4b877f8a6d0377e9b6b3b858cd378129a0171f68eee9518e96e7977a23827fa2f5a81438477ab1180df64de157119ccce4343f5b341e9cd4bd32b42d56b322c23a4ee3c070a06e311eac72f5bd4aea7ae18036a0881640d70078c27bac7c75acd24850d4be61221d2f85b495237b476fbdc2b13ff0a0e8f1601d3e4e04b12b6cdcbd9c9020c86fe2881967f8ef593b1774d1193755313362f7c40fe8c74acc205cf5ddfaa4a797d6f22e2b524e51dd825eae1d0eee429b791d63d6790ca540b8c58ac84edf9d6dd4383b7ca07e1d06867588ab70e20a32520b3dae5502f04c5d106703b5c79422f35d85036c21753f5fbf5555e9232a743d4772008de6cd1e3fdc5be9e86c709081f905094bf86fc59932b3536d1145875794c39f000bf64c905aa8fc39d6e1ada484e7530247aa7c274a3ce610bd14257fb619c5b9e1b05b0314f3934dbd42816d827cdf7b2ad3a6b09465bdf7a58534840be92daa724cde0a1a58daab1cd34a15416fd9ad809a865689314b5302f35e808f34c63b7fe18d50938391a38aedc40fd7580f482ec71edb0783961943811640887e2f2375e36e02fd1801224d2226d4c504aaf24e2481a3290056ac28f2a40bc9cfa9ad3624c3e4df3eddd3b69104b18f558f6f70fcdf15e2736c20926fce8fbfcb186c70c41066831ec0558276828b558fd1d2b18b45c4df7f75e40c218844a664803faf4b7a39ef45d3cdd54fa5b7b54af75cef019e6cc2f181489c20d946297711ab5173fa650694a3d85e601b543ea09730d48c082ea6e5132a8e2c9c3a75e83ff4588808b75000454a41a567dcbbf1117ec0b2a44e4074cb1d060ea5fc6cd4247b811b8d3fde7c84d569a11a1510d9713ed22cd19456b77bc419b603302af7a94a5af514c451860a9e44b9aad1b6ae296e22b8a092b0f6e3d44535c5fc04ec071581fd2a4a33317a62934d2308d85f9674243cec33052d6caff0578cdb9aec44442707bdfa7e2eda441b127ec87309c5615981841a320df2fa0f64e8c5a82f7bf3470a4f980903b01e841748461c42d50a50bdb27087dfcdb8830b541b69ec19f3d0b3d0f2853675be17715fcf104e27dc6c7a9107768b11ac6ebf982766b80ceb20fc774b92a6b46656b0a11a3469cb23daeacb2702662ed2f73f397718cce87386793e9ae38325dd92f252e6bcd846a3f183ec67e1123e0d9abad8d64defa7b6a20b00b24a2b36c90538fb5cb8d5c2214712eb78ba69261f487532102a94b622f2ebc5bcacc0e355054451c91c57c07e62429adc8638236d7e310a52b34e6639974d5a8c874e4fc4abc8471479174fd7b3807f7f18088e68213f38c790ce1a9164824b162509adf70d458a15f27e49037b2a6623ecd852fc839ec33bd79919fd25892ada5aeb17724796899481ec7c77a4c3b1d07d5aea8a778d7f59f3de79641f9215ea5bbb25421545b7829878235e0c6ba7773c6040fd00039ee5c25a684e7f25930f52c87cc4cba46c02b10d556a750cdc4d521d918c1c15c9d49b1cff9a25372532ed1e5f2b7c669bacf095af7e407ff664fa7de04faeb8f2e6e82927be5bb2a4e34c9d24b33bb33daee1b260a5822c82fa4a648b3eb6132650ce6e0d464f50e8c5d7c02b2d6ede3b88dd0358825eb8ea82cde001789040f1ef8c7a760cc922dd18304d002d39cb0328de50f25c15a93e741579a4cffd49b263b2f558699b9cac19c3458d5087f5016a9a5ce84a140c4e6531de14c95cbf54fd26ffece69433f6bdf3aa6c4b79c2fec89497a344b82098c8a1048d07ccbfa005269daa2617ebbbe053dbd998dfee7b39caf14b9b99ba2172d3c49ac86727a66e9026b25308e0109bdeb8ecb23741e12aaaa702c9dc11fd39fca519ebc54db30b70c92a693397bc37ea6118599de5bfdca3e3c7daa8e89df0b0e749701ebbefc29a19a58750a2634e02fe9a34c5332e9b24e231f69fe0fa703fce699e1b00d5879ef41781442fb0ee8f9d52514955854af6c4abcea87b2c9e41b3b7031fba113f19d0ab51f15e9ddf6b962c77fa8e5844929449e9edb6c7257a7a47bdb775da7f5f448750389504d9e601c9bd4abee91d6488576cc89a5665f4dc0c28f3d830ac8ec34b603e8d846fcb07ca79aaeb1ef3ff83371f88cf40e016c5bbd8de290049ae0476b601913c36ef3b514f37e9e911114926540a540c983f8b9c02d25854a8c573b19f384eecc78c42c8a441c375638976a0edb50f540819d354f77c4a5c7f37c12a50329f3e7a07a6c44dbef852677c09d9c6933c2bb64e08976c0408ad3e2ae4c2269ba609ad61f215e98b83275c904346d08254bc42bde55f581577d9f0c09e06ac051051567f14a87627c115a5c2464cdf91c2b0934d3c6e5fc8b6335afe3653874a605805029d766223f50e20593963167e9cadff897b0a6900b90b38888d952eeb87cbfdfdafbfba8f486720949e774a57bc844b163586472d04883c26d6ae50ed5409799391e79ae4c809bafd79270d061d433049c753a09182376f797bc7954d745b8561ae71af4060bedc2ed33312338bfbb979a92417c29f8f611f6011016d64ae6496054928f7d3294c54bc989235494b7eff786dc6854c410586df8564885f543212a79e073a591e6655ad839903a045eb302fa4690ae1956bc3e064c3816e6c9628631453fe54c4f9a0d9bf7c8fc4e0bd1e6d8a8976cf7dfb3d70659c970289111bdb2e454ea593083b2278f8912b24c2ae5a4c17bf4c6d2060775fc68b5a3edeba3486b104b6fbbf50c60768e5e097ad2eb696f6ba72a8ab0a2498e832f34ea5358670c3e1d318c2bfa92d77267ac6ed426bfefe62d52e6a441ab8bb1fd12f3f4fd5ebd55c7396a58bf8776b283743b7d9ccf95f9d6f9d391983f7caec689b732d1feb9c1777281100b0b554945917e5b06fc282965a51640d3be6a94c517ea8e1e64b70d85034f4779048ba356d6cf9a740eb0023da246a0e0e6695245e36c5e648360c0418354dbb77156831ae6f6473ba32025790b96e4a64e36106533bb82cd5fb587145a7a5c17d4532f086e8d50be2955d6fff7f4fd70ebdad14e6b617bbc9decf089208e9d3fa95c65b0ed030523e0909633438f2acfeb941830a7e0c9d192ee701b516032ebf30c05b9157a7a07a52de0e8ff91d68a1a387a2b699ee6bdf25cafd5c5485ab627a13621a4bd6e6dc77bc67921a6953358a81300c3272a0c7f566238af3fc0c9bb0d59d00b00b40cb8a6c99a21db3265dc4622890051107543dd0422cfd6dccf175a01c6f3b220b39c33895c1f71603e4ecde7bb822f22e749813ff9a29b96d96d75bdbb7ccd8899f235f6104da79f46185414c727d7f56549633447fbcd07f236aa124930ee17271976158f2e906016affebe62792336ed683cb623894dd03684e147d59b085ed0d3e427a41cbbe34500d718cb83498d6343d3438082e0137749caf53df473474b4f94c75c46049e4eccdfafae606a2e27188cba0954a3c58236ddc024e0902bd44025a5b0cb0222df439bb95acd005643014db011ecea10b263a54f72e5526d36584f0863595d6cfbb102e0fb17b651c78bafa62d399f41d937c8eabb5a9ef09bfbf507064dae184fadd36d99b33474b8bb777c1a15978ff99116326ca2dab986415785db33baabfe889681d10f5cb64a72bbac9c39113419cb9c7e0d795b5652178e05ba11630ffa48595a9eddfc33665df5605d29958576795ee2348679e7a4b05856e10dbcfd6b96fe995d0ff388381954eb48e4007ec738160743e86d4b3583f50d80bb86f45c7b37f9cd9a3d97b968654c50a7143a0d2cb0e2a406dbcf38bc2536ee6235600f3a9b15d27014d854646005e41cd39f1232924177a2c8cdade67182b5d392056e31205adc960f25d4926a75914d27a2a74823d089acf91a0a4c9ffe985d4ab3dc88c3819383ce8df26909020dbc60cd67e72a9c4bfd2dcab30b1398c37a20b73f7d4cb4bcf6fabb93b33638b82058aed007e46d9e6cd65684f454fe2e3151e901468b7d81a7311184f95f493d2aa549e78f5b929bb5f8f67776310d19089ae539cd9687536aacad87fef9a0381625eb36c24263de9cb796f77d838f1a58d41893de62e71c4010513dcaa4d4e45b2e7aa3ea1183b338988611858bc3bc8fa95241415570c1a6da30b74374569d37ca208d4baa36cb08368cbc250483a54d117c91deb7ef21b2e89e41f2fbe0a7d2796db0426b5aac715c8427506b8f41550b80b93ec10c7a83b31f76745b20e30e04f95f3176437551d163a2bfd4bc310a965ed45ad8b892cfa4a71905afeaa8dc87759dda93370f88530b06de6a7236e0d342b852260a813fa038210e5b258d223730598cc536c71ae1e4caea4a07bfffac1af131c548e3fd340a25c56203b4b7421ed6182b61f20b3d3c94f1f1a1e29c46112bbf27e46d73d764faca42cf6f1258233798482cd735f46c3fbf659ab0c3a6bab95e19a22064e55bf6c268bdf5d30b4d8b01c4969deae140c5aa9c7f67eadfe46156fb8320c237567c774c339d2f7e213d9017c3d0ad62ce3863e21c6ca259fcb79bbe476d976570d221db390ef24fba7a44e66df0c7b6d3ea6c7e082dae53ea514fd87f03b054901e7cb94ec8754aaf6432c8e29b519483cf4b426a0a080a637df6f8311faf1902d3a8d5c12f7f0ec34128a5794941f889c0523ef2a083e38d8ac47a36a42f584ca5621b148b3d430fa4d92dd3045c0ff274553dcbc7cda13b9b64d14b9e12486c12f6cdbefbae79f7dfa7fbed6db994697eb1017d2a48bd9c4786b41a5f152aaab1c2218b60f64cda15c22a458becd9d3df2ef1a29d428e60723bc822702e98c27039171f6b984ae38087bc77e334d8c31885a6e18af0ca71c48ddbb270ed8f2320ae122b9503a5b9c7e71bf858829808d828566e0723843d578b3a557a6d83f0a77c817bd2543a1371d804395bf889fba722552be6c3bc2fa792275dc34812660ee41c90f442b069f3f88297d6c9d06d7ded68f5afa25036019b654042e7708c3e071a16bc8da64ba3e56bc203cca104279e5bf4277c96d564c416d966b0521811769fc81bd12db67637ab82f14b9afbad6381fc09b97631fc8d458a172d41087882e0a68d077cc4fd0a2cfcff04a858f9114b10b2b46ee5be86023ca38bc72507651d441f187c20235b0399853caa064d45dae054d084149f4c15f1b2bd3041ae68cf58bfffafeee340d9c19952c6167252ebeaf32ab8870ace1a6bb2007db0e05148fcf1aa701863d89584003846b883f013e8d0350558b8375ba725e9f960eda24c3a548ca662ba67a15d616566d1c24d358a1c096981fabed1b50591c9f20d5df274dfd0e66bc49a6330f7a5fe85fbd954f68feb75cbc5db6b493994b8fe9a4cd03cb660b3224236956789781a541a2b1c8235e1225f251d0cde03e8b397391d6bd84e26f9ceb885ac12bbfd3846abf0f96995d763b2bf5f8953bc84c20f11d68f32ec4bcaa71158933f7544d7ec45466e3edb6121193c6c46d60f3968892da67e62ef9a8a5394326e6bced6a65081663e46bade2d17b8a24ff7dbe91a2a1544e134671898a56151456379ea9a2e98bdd31890108a7b51fa69947684087c333a623fa92cf8d6a736e443a8795984808df34108ade77e0787964109e0935e2110303755b410073295e6b9a108f159e54ad58ae7e41c9085bd01820332bc032c2e74701487b11c8cd4b1a6890a8843523419be326c838ac5bffa820924e002318b4cf5ffc4a357e2d7d9cb92601ce41ea46dc53f32d0b25eaab690e4806daa3152d51a544724eefa7c67e498a23e91221cd774a19d45386f77dc9c8ecd017c2ce52646ba358c6fbb8821924b5459d6e8586f1f413dee1c8e0bc66a2669099db5c0bc53f2589aff99db1f0c032eb8c0de1fcfb025546e351c18c90bb05dd37b3ed163d39e3b8f8e00ae2635655d4a087b757f213fe396bd8a925afaa14e18e18bd8b38a0405fa57df2bc80622f039168823a1f72ff41544e5c3e169d0a6de8a507299785fe7690f6103060a114172a0a3b6cd23597f8a959eb0b9c6e6e85e7601096f72cddc1e555fa4d3d815285c71d0d4ff1c334c50768998afcbfe2e464f686e7280edf700000000000000000000000000000a15171d232a343d\"}";
        JSONObject json=JSONObject.parseObject(jsonstr);
        String payloadJson=json.getString("payloadJson");

        TransactionData recdata=TransactionData.fromJson(payloadJson);
        LogObj.println("欢迎交易对象："+recdata.toJson());*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        }

        generateGenesisWallets(10);  //创建10个钱包密钥对
    }













    public static void generateGenesisWallets(int count) throws Exception {
        JSONArray walletsArray = new JSONArray();
        System.out.println("========== ✨ Genesis Wallet Generate ✨ ==========");
        for (int i = 0; i < count; i++) {
            // 创建钱包对象
            QccWallet wallet = QccWallet.creatQccWallet();
            JSONObject w = new JSONObject();
            w.put("index", i);
            w.put("address", wallet.address);
            w.put("publicKeyHex", wallet.publicKeyHex); // 建议统一字段名
            w.put("privateKeyHex", wallet.privateKeyHex);
            walletsArray.add(w);
            System.out.println(String.format("[%d] Address: %s", i, wallet.address));
        }

        // 构建最终存储对象
        JSONObject result = new JSONObject();
        result.put("wallets", walletsArray);
        result.put("count", count);
        result.put("generatedAt", LocalDateTime.now().toString());
        // 确定保存路径 (自动创建目录)
        File file = new File("genesis_wallets.json");
        // 修正点：使用 JSONWriter.Feature 进行格式化控制
        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(file), StandardCharsets.UTF_8)) {
            // FastJSON2 正确的格式化输出写法
            String jsonString = JSON.toJSONString(result, JSONWriter.Feature.PrettyFormat);
            writer.write(jsonString);
        }
        System.out.println("============================================");
        System.out.println("✅ 保存成功: " + file.getAbsolutePath());
        System.out.println("============================================");
    }


    /**
     * 从本地 JSON 还原钱包对象列表
     */
    public static List<QccWallet> loadGenesisWallets() throws Exception {

        List<QccWallet> walletList = new ArrayList<>();
        File file = new File("genesis_wallets.json");

        if (!file.exists()) {
            throw new FileNotFoundException("❌ 未找到创世钱包文件: genesis_wallets.json" );
        }

        // 读取文件内容
        String content;
        try (FileInputStream fis = new FileInputStream(file)) {
            content = new String(fis.readAllBytes(), StandardCharsets.UTF_8);
        }

        // 解析 JSON
        JSONObject root = JSON.parseObject(content);
        JSONArray wallets = root.getJSONArray("wallets");

        for (int i = 0; i < wallets.size(); i++) {
            JSONObject obj = wallets.getJSONObject(i);

            // 使用构造函数还原对象 (假设构造函数顺序为: address, privateKey, publicKey)
            // 请根据你 QccWallet 类的构造函数实际参数顺序调整
            QccWallet wallet = new QccWallet(
                    obj.getString("address"),
                    obj.getString("privateKeyHex"),
                    obj.getString("publicKeyHex")
            );
            walletList.add(wallet);
        }

        System.out.println("✅ 成功还原钱包数量: " + walletList.size());
        return walletList;
    }


    private static CompletableFuture<String> sendTransactionAsync(
            QccWallet from,
            QccWallet to,
            long amount,
            String assetId
    ) {
        String packetJson = buildPacket(from, to, amount, assetId);
        LogObj.println("交易数据string:"+packetJson);
        if(!packetJson.isEmpty()) return null;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SERVER_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(packetJson))
                .build();

        return null;
    }

    private static final String SERVER_URL = "http://192.131.142.226:8080/v1/txm";

    // =========================
    // 构建交易包（通用资产）
    // =========================
    private static String buildPacket(QccWallet from, QccWallet to, long amount, String assetId) {
        TransactionData data = new TransactionData();
        data.id = ToolsUtil.getUUID();
        data.from = from.address;
        data.to = to.address;
        data.amount = amount;
        data.assetId = assetId;
        data.txType = "pay";

        return QccDataProtocol
                .encodeData(data, from.getPrivateObj(), from.publicKeyHex)
                .toJson();
    }




}