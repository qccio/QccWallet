// QubePath.java 终极跨平台版（2025年11月23日全球最硬！！！）
package qcc.config;

import java.nio.file.Files;
import java.nio.file.Path;

public class QCCPath {

    // 自动获取用户主目录下的qubechain文件夹！！！Mac/Linux/Windows全兼容！！！
    private static final String USER_HOME = System.getProperty("user.home");
    public static final Path ROOT_DIR = Path.of(USER_HOME, "qccchain");

    // 所有子目录（全自动派生！！！）
    public static final Path DATA_DIR         = ROOT_DIR.resolve("data");         // 历史容器
    public static final Path CONTAINER_DIR    = ROOT_DIR.resolve("containers");  // 正在生产
    public static final Path CACHE_DIR        = ROOT_DIR.resolve("cache");       // 缓存
    public static final Path BROADCAST_OUT    = ROOT_DIR.resolve("broadcast/out");
    public static final Path BROADCAST_REC    = ROOT_DIR.resolve("broadcast/rec");
    public static final Path WALLET_DIR       = ROOT_DIR.resolve("wallet");
    public static final Path LOG_DIR          = ROOT_DIR.resolve("logs");

    public static void init() {
        try {
            // 创建所有目录（权限100%够！！！）
            Files.createDirectories(ROOT_DIR);
            Files.createDirectories(DATA_DIR);
            Files.createDirectories(CONTAINER_DIR);
            Files.createDirectories(CACHE_DIR);
            Files.createDirectories(BROADCAST_OUT);
            Files.createDirectories(BROADCAST_REC);
            Files.createDirectories(WALLET_DIR);
            Files.createDirectories(LOG_DIR);

            System.out.println("QCCChain ∞ 路径初始化完成！！！"+USER_HOME);
            System.out.println("主目录: " + ROOT_DIR.toAbsolutePath());


        } catch (Exception e) {
            throw new RuntimeException("路径初始化失败！！！", e);
        }
    }
}