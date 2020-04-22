package q008;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Q008 埋め込み文字列の抽出
 * <p>
 * 一般に、定数定義の場合を除いて、プログラム中に埋め込み文字列を記述するのは良くないとされています。
 * そのような埋め込み文字列を見つけるために、埋め込み文字列や埋め込み文字（"test"や'a'など）が
 * 記述された行を出力するプログラムを作成してください。
 * <p>
 * - 実行ディレクトリ配下（再帰的にチェック）に存在するすべてのjavaファイルをチェックする
 * - ファイル名はディレクトリ付きでも無しでも良い
 * - 行の内容を出力する際は、先頭のインデントは削除しても残しても良い
 * <p>
 * [出力イメージ]
 * Q001.java(12): return "test";  // テストデータ
 * Q002.java(10): private static final DATA = "test"
 * Q002.java(11): + "aaa";
 * Q002.java(20): if (test == 'x') {
 * Q004.java(13): Pattern pattern = Pattern.compile("(\".*\")|(\'.*\')");
 */
public class Q008 {
    /**
     * JavaファイルのStreamを作成する
     *
     * @return
     * @throws IOException
     */
    private static Stream<File> listJavaFiles() throws IOException {
        return Files.walk(Paths.get(".")).map(Path::toFile).filter(f -> f.getName().endsWith(".java"));
    }

    public static void main(String[] args) {
        List<File> fileList = null;
        try {
            fileList = listJavaFiles().map(file -> new File(file.getPath())).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("get file list failed. message:" + e.getMessage());
            return;
        }
        for (File file : fileList) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
                String line = reader.readLine();
                int index = 1;
                while (line != null) {
                    if (line.contains("'") || line.contains("\"")) {
                        System.out.println(String.format("%s(%d): %s);", file.getName(), index, line));
                    }
                    line = reader.readLine();
                    index++;
                }
            } catch (FileNotFoundException e) {
                System.out.println("file not found. message=" + e.getMessage());
            } catch (IOException e) {
                System.out.println("read file failed. message=" + e.getMessage());
            }
        }
    }
}
// 完成までの時間: 0時間 35分
