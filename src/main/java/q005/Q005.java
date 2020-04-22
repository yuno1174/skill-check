package q005;

import q003.Q003;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Q005 データクラスと様々な集計
 *
 * 以下のファイルを読み込んで、WorkDataクラスのインスタンスを作成してください。
 * resources/q005/data.txt
 * (先頭行はタイトルなので読み取りをスキップする)
 *
 * 読み込んだデータを以下で集計して出力してください。
 * (1) 役職別の合計作業時間
 * (2) Pコード別の合計作業時間
 * (3) 社員番号別の合計作業時間
 * 上記項目内での出力順は問いません。
 *
 * 作業時間は "xx時間xx分" の形式にしてください。
 * また、WorkDataクラスは自由に修正してください。
 *
[出力イメージ]
部長: xx時間xx分
課長: xx時間xx分
一般: xx時間xx分
Z-7-31100: xx時間xx分
I-7-31100: xx時間xx分
T-7-30002: xx時間xx分
（省略）
194033: xx時間xx分
195052: xx時間xx分
195066: xx時間xx分
（省略）
 */
public class Q005 {
    public static void main(String[] args) {

        List<WorkData> workDateList = new ArrayList();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Q005.class.getResourceAsStream("data.txt")))) {
            workDateList = reader.lines().skip(1).map(Q005::createWorkData).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            System.out.println("file not found. message="+ e.getMessage());
        } catch (IOException e) {
            System.out.println("read file failed. message="+ e.getMessage());
        }

        // (1) 役職別の合計作業時間
        System.out.println("役職別の合計作業時間");
        Map<String, List<WorkData>> groupByPosition =
                workDateList.stream().collect(
                        Collectors.groupingBy(WorkData::getPosition));
        printCollectTime(groupByPosition);

        // (2) Pコード別の合計作業時間
        System.out.println("Pコード別の合計作業時間");
        Map<String, List<WorkData>> groupByPCode =
                workDateList.stream().collect(
                        Collectors.groupingBy(WorkData::getPCode));
        printCollectTime(groupByPCode);

        // (3) 社員番号別の合計作業時間
        System.out.println("社員番号別の合計作業時間");
        Map<String, List<WorkData>> groupByNumber =
                workDateList.stream().collect(
                        Collectors.groupingBy(WorkData::getNumber));
        printCollectTime(groupByNumber);
    }

    private static WorkData createWorkData(String line){
        String[] splittedLine = line.split(",");
        return new WorkData(
                splittedLine[0],
                splittedLine[1],
                splittedLine[2],
                splittedLine[3],
                Integer.parseInt(splittedLine[4]));
    }

    private static void printCollectTime(Map<String, List<WorkData>> groupedWorkData){
        groupedWorkData.entrySet().stream().sorted(Comparator.comparing(m -> m.getKey()))
                .forEach(e -> {
                    int min = e.getValue().stream().mapToInt(WorkData::getWorkTime).sum();
                    int hour = min / 60;
                    min = min % 60;
                    System.out.println(String.format("%s: %02d時間%02d分",
                            e.getKey(),
                            hour,
                            min));
                });
    }
}
// 完成までの時間: 0時間 40分