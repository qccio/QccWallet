package qcc.util;

// GzipUtil.java —— 全球最硬压缩解压工具（零依赖！Java原生！永封版）
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class GzipUtil {

    // 压缩（最硬！）
    public static byte[] compress(byte[] data) {
        if (data == null || data.length == 0) return data;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             GZIPOutputStream gzip = new GZIPOutputStream(baos)) {

            gzip.write(data);
            gzip.finish();
            return baos.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("GZIP压缩失败", e);
        }
    }

    // 解压（最硬！）
    public static byte[] decompress(byte[] compressed) {
        if (compressed == null || compressed.length == 0) return compressed;

        try (ByteArrayInputStream bais = new ByteArrayInputStream(compressed);
             GZIPInputStream gzip = new GZIPInputStream(bais);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[8192];
            int len;
            while ((len = gzip.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("GZIP解压失败", e);
        }
    }

    // 私有构造，禁止实例化
    private GzipUtil() {
        throw new AssertionError("GzipUtil 不能实例化");
    }
}