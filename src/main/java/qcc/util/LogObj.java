package qcc.util;

import java.io.File;

/**
 * 安全日志门控 (升级版)
 * 逻辑优先级：
 * 1. 默认：关闭 (Release)
 * 2. 初始检测：如果有 "debug" 文件 -> 开启 (Debug)
 * 3. 最终裁决：如果 Config 加载后发现是生产环境 -> 强制关闭 (Force Release)
 */
public class LogObj {

    // ⚠️ 去掉 final，允许运行时修改。
    // volatile 保证多线程可见性，防止其他线程还在输出日志
    private static volatile boolean isDebugMode = false;

    // 静态块：第一轮检测 (基于文件)
    static {
        File debugMarker = new File("debug");
        if (debugMarker.exists()) {
            isDebugMode = true;
            // 使用 System.out 打印初始状态，防止死循环
            System.out.println(">>> [LogObj] 初步检测到 'debug' 文件，日志系统已就绪。");
        }
    }

    /**
     * 🚀 核心安全方法：强制进入 Release 模式
     * 这是一个"单向阀门"，一旦调用，日志将永久关闭，无法再通过代码打开。
     */
    public static void enableReleaseMode() {
        if (isDebugMode) {
            System.out.println(">>> [LogObj] 🛡️ 识别到生产环境配置，强制关闭所有日志输出！(Log Shutdown)");
            isDebugMode = false;
        }
    }


    // --- 下面是修复后的打印方法 (替换 System.out) ---

    public static void println(Object x) {
        if (isDebugMode) {
            System.out.println(x); // ✅ 修复：必须调 System.out，不能调 LogObj.println
        }
    }

    public static void println(String x) {
        if (isDebugMode) {
            System.out.println(x);
        }
    }

    public static void errprintln(String x) {
        if (isDebugMode) {
            System.out.println(x);
        }
    }


    public static void print(Object x) {
        if (isDebugMode) {
            System.out.print(x);
        }
    }

    public static void printf(String format, Object... args) {
        if (isDebugMode) {
            System.out.printf(format, args);
        }
    }

    public static void error(String msg, Throwable t) {
        if (isDebugMode) {
            System.err.println(msg);
            t.printStackTrace();
        }
    }

    // 提供给业务做逻辑判断 (如避免执行耗时的 JSON 序列化)
    public static boolean isDebug() {
        return isDebugMode;
    }
}