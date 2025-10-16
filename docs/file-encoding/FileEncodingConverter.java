import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

public class JavaFileConverter {

    // 假设源文件编码是 GBK。请根据你的实际情况修改。
    private static final Charset SOURCE_CHARSET = Charset.forName("GBK");
    private static final Charset TARGET_CHARSET = Charset.forName("UTF-8");

    public static void main(String[] args) throws IOException {
        String path = args.length > 0 ? args[0] : ".";
        Path startPath = Paths.get(path); // 当前目录

        Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".java")) {
                    System.out.println("Processing: " + file.toAbsolutePath());
                    convertFileEncoding(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        System.out.println("All .java files converted to UTF-8.");
    }

    private static void convertFileEncoding(Path filePath) throws IOException {
        // 读取文件内容，使用源编码
        String content = new String(Files.readAllBytes(filePath), SOURCE_CHARSET);

        // 将内容以目标编码写入回文件
        Files.write(filePath, content.getBytes(TARGET_CHARSET));
    }
}