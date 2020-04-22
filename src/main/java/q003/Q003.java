package q003;

import q002.Person;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Q003 集計と並べ替え
 * <p>
 * 以下のデータファイルを読み込んで、出現する単語ごとに数をカウントし、アルファベット辞書順に並び変えて出力してください。
 * resources/q003/data.txt
 * 単語の条件は以下となります
 * - "I"以外は全て小文字で扱う（"My"と"my"は同じく"my"として扱う）
 * - 単数形と複数形のように少しでも文字列が異れば別単語として扱う（"dream"と"dreams"は別単語）
 * - アポストロフィーやハイフン付の単語は1単語として扱う（"isn't"や"dead-end"）
 * <p>
 * 出力形式:単語=数
 * <p>
 * [出力イメージ]
 * （省略）
 * highest=1
 * I=3
 * if=2
 * ignorance=1
 * （省略）
 * <p>
 * 参考
 * http://eikaiwa.dmm.com/blog/4690/
 */
public class Q003 {
    /**
     * データファイルを開く
     * resources/q003/data.txt
     */
    private static InputStream openDataFile() {
        return Q003.class.getResourceAsStream("data.txt");
    }

    public static void main(String[] args) {
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(openDataFile()))) {
                List<String> collectWordList = new ArrayList();

                String line = reader.readLine();
                while(line != null){
                    // 不要な文字の除去
                    line = line.replace(",", "");
                    line = line.replace(".", "");
                    line = line.replace(" – ", " ");
                    line = line.replace(";", "");
                    line = line.replace("'", "’");

                    collectWordList.addAll(Arrays.asList(line.split(" ")));

                    System.out.println(line);
                    line = reader.readLine();
                }
                Map<String, List<String>> groupByWord =
                collectWordList.stream().sorted().collect(
                        Collectors.groupingBy(Q003::toLowerCase));
                groupByWord.entrySet().stream().sorted(Comparator.comparing(m -> m.getKey()))
                        .forEach(e -> {
                            System.out.println(String.format("%s=%d", e.getKey(), e.getValue().size()));
                        });
            }
        } catch (IOException e) {
            System.out.println("read Resource failed.");
        }
    }

    private static String toLowerCase(String word){
        if("I".equals(word)){
            return word;
        }
        return word.toLowerCase();
    }
}
// 完成までの時間: 0時間 45分