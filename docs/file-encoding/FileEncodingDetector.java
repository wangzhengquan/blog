import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.List;

public class FileEncodingDetector {

    // 常用编码列表，按优先级从高到低排列（你可以根据你的场景调整）
    private static final List<Charset> COMMON_CHARSETS = Arrays.asList(
            Charset.forName("UTF-8"),          // 最常见，优先检测BOM
            Charset.forName("GBK"),            // 简体中文常用
            Charset.forName("Big5"),           // 繁体中文常用
            Charset.forName("windows-1252"),   // 西欧语言，Windows默认之一
            Charset.forName("ISO-8859-1")      // 通用西欧，广泛使用
    );

    /**
     * 尝试检测文件的编码。
     * 1. 首先尝试检测 BOM。
     * 2. 如果没有 BOM，则按 COMMON_CHARSETS 列表的顺序尝试解码。
     * 3. 认为第一个能够完全解码文件内容且不报告错误的编码是最佳猜测。
     *
     * @param filePath 文件路径
     * @return 检测到的 Charset，如果无法确定则返回 null
     * @throws IOException 文件读取错误
     */
    public static Charset detectFileEncoding(String filePath) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
            // 1. 尝试检测 BOM (UTF-8, UTF-16)
            bis.mark(4); // 标记前4个字节，以便重置
            byte[] bomBytes = new byte[4];
            int bytesRead = bis.read(bomBytes);
            bis.reset(); // 重置流到文件开头

            if (bytesRead >= 3 && bomBytes[0] == (byte) 0xEF && bomBytes[1] == (byte) 0xBB && bomBytes[2] == (byte) 0xBF) {
                return Charset.forName("UTF-8"); // UTF-8 BOM
            }
            if (bytesRead >= 2 && bomBytes[0] == (byte) 0xFE && bomBytes[1] == (byte) 0xFF) {
                return Charset.forName("UTF-16BE"); // UTF-16BE BOM
            }
            if (bytesRead >= 2 && bomBytes[0] == (byte) 0xFF && bomBytes[1] == (byte) 0xFE) {
                return Charset.forName("UTF-16LE"); // UTF-16LE BOM
            }

            // 2. 如果没有 BOM，则尝试列表中的编码
            // 读取所有字节到内存，因为需要多次尝试解码
            byte[] fileBytes = bis.readAllBytes();

            for (Charset charset : COMMON_CHARSETS) {
                if (tryDecode(fileBytes, charset)) {
                    return charset;
                }
            }

            // 3. 如果以上都失败，则尝试平台默认编码 (作为最后的尝试)
            Charset platformDefault = Charset.defaultCharset();
            if (tryDecode(fileBytes, platformDefault)) {
                return platformDefault;
            }

            return null; // 无法确定编码
        }
    }

    /**
     * 尝试用指定的编码解码字节数组，并检查是否有编码错误。
     *
     * @param bytes   文件的所有字节
     * @param charset 尝试的编码
     * @return 如果可以成功解码且没有错误，则返回 true
     */
    private static boolean tryDecode(byte[] bytes, Charset charset) {
        try {
            CharsetDecoder decoder = charset.newDecoder();
            // 报告错误，而不是替换或忽略
            decoder.onMalformedInput(CodingErrorAction.REPORT);
            decoder.onUnmappableCharacter(CodingErrorAction.REPORT);

            // 尝试解码
            decoder.decode(java.nio.ByteBuffer.wrap(bytes));
            return true; // 成功解码，没有报告错误
        } catch (java.nio.charset.CharacterCodingException e) {
            return false; // 解码失败或有错误
        }
    }

    public static void main(String[] args) {
        String testFilePath = args.length > 0 ? args[0] : "./DatePickerWin.java"; // 替换为你的测试文件路径

        try {
            Charset detectedCharset = detectFileEncoding(testFilePath);
            if (detectedCharset != null) {
                System.out.println("Detected encoding for " + testFilePath + ": " + detectedCharset.name());
                // 示例：用检测到的编码读取文件内容
                // String content = new String(Files.readAllBytes(Paths.get(testFilePath)), detectedCharset);
                // System.out.println("File content (first 100 chars):\n" + content.substring(0, Math.min(content.length(), 100)));
            } else {
                System.out.println("Could not reliably detect encoding for " + testFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * > javac ./FileEncodingDetector.java 
 * > java FileEncodingDetector
 */