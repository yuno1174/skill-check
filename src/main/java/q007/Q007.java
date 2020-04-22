package q007;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * q007 最短経路探索
 * <p>
 * 壁を 'X' 通路を ' ' 開始を 'S' ゴールを 'E' で表現された迷路で、最短経路を通った場合に
 * 何歩でゴールまでたどり着くかを出力するプログラムを実装してください。
 * もし、ゴールまで辿り着くルートが無かった場合は -1 を出力してください。
 * なお、1歩は上下左右のいずれかにしか動くことはできません（斜めはNG）。
 * <p>
 * 迷路データは MazeInputStream から取得してください。
 * 迷路の横幅と高さは毎回異なりますが、必ず長方形（あるいは正方形）となっており、外壁は全て'X'で埋まっています。
 * 空行が迷路データの終了です。
 * <p>
 * <p>
 * [迷路の例]
 * XXXXXXXXX
 * XSX    EX
 * X XXX X X
 * X   X X X
 * X X XXX X
 * X X     X
 * XXXXXXXXX
 * <p>
 * [答え]
 * 14
 */
public class Q007 {
    public static void main(String[] args) {

        List<String> list = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new MazeInputStream()))) {
            list = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        list.stream().forEach(System.out::println);
        char[][] mazeList = new char[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            mazeList[i] = list.get(i).toCharArray();
        }

        for (int y = 0; y < mazeList.length; y++) {
            for (int x = 0; x < mazeList[0].length; x++) {
                if (mazeList[y][x] == 'S') {
                    int nx = x;
                    int ny = y;
                    System.out.println(String.format("Sの位置: x(%d), y(%d)", x, y));
                    System.out.println("最短距離: " + explore(mazeList, x, y, -1));
                    return;
                }
            }
        }

    }

    private static int explore(char[][] mazeList, int targetX, int targetY, int count) {

        if (mazeList[targetY][targetX] == 'X'
                || mazeList[targetY][targetX] == '%') {
            return -1;
        }

        count++;

        if (mazeList[targetY][targetX] == 'E') {
            /* ルートテスト用の標準出力
            for (int y = 0; y < mazeList.length - 1; y++) {
                for (int x = 0; x < mazeList[0].length; x++) {
                    System.out.print(mazeList[y][x]);
                }
                System.out.println();
            }
            System.out.println("count: " + count);
            */
            return count;
        }

        if (mazeList[targetY][targetX] != 'S') {
            mazeList[targetY][targetX] = '%';
        }

        List<Integer> exploreNoList = new ArrayList<>();
        exploreNoList.add(explore(mapClone(mazeList), targetX, targetY - 1, count));
        exploreNoList.add(explore(mapClone(mazeList), targetX, targetY + 1, count));
        exploreNoList.add(explore(mapClone(mazeList), targetX - 1, targetY, count));
        exploreNoList.add(explore(mapClone(mazeList), targetX + 1, targetY, count));

        return exploreNoList.stream().filter(i -> i > 0).min(Comparator.naturalOrder()).orElse(-1);
    }

    private static char[][] mapClone(char[][] mazeList) {
        char[][] cloneList = new char[mazeList.length][];
        for (int i = 0; i < mazeList.length; i++) {
            cloneList[i] = mazeList[i].clone();
        }
        return cloneList;
    }
}
// 完成までの時間: 2時間 23分