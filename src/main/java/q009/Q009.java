package q009;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Q009 重い処理を別スレッドで実行
 * <p>
 * 標準入力から整数を受け取り、別スレッドで素因数分解して、その整数を構成する2以上の素数を求めるプログラムを記述してください。
 * - 素因数分解は重い処理であるため、別スレッドで実行してください
 * - 標準入力から整数を受け取った後は、再度標準入力に戻ります
 * - 空文字が入力された場合は、素因数分解を実行中の状態を表示します（「実行中」あるいは処理結果を表示）
 * - 素因数分解の効率的なアルゴリズムを求めるのが問題ではないため、あえて単純なアルゴリズムで良い（遅いほどよい）
 * （例えば、2および3以上の奇数で割り切れるかを全部チェックする方法でも良い）
 * - BigIntegerなどを使って、大きな数値も扱えるようにしてください
 * [実行イメージ]
 * 入力) 65536
 * 入力)
 * 65536: 実行中  <-- もし65536の素因数分解が実行中だった場合はこのように表示する
 * 入力) 12345
 * 入力)
 * 65536: 2      <-- 実行が終わっていたら結果を表示する。2の16乗だが、16乗の部分の表示は不要（複数溜まっていた場合の順番は問わない）
 * 12345: 実行中
 * 入力)
 * 12345: 3,5,823
 */
public class Q009 {

    public static void main(String[] args) {
        Map<Integer, CalcPrimeValueStatus> inputMap = new HashMap();
        String inputNumber;
        int key = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("整数を入力してください。そのほか) 無入力:処理状況, 0:処理中断");
                inputNumber = br.readLine();
                if (inputNumber.equals("")) {
                    inputMap.forEach((k, v) -> {
                        System.out.println(String.format("%d: %s",
                                v.getTargetValue(),
                                v.getRunStatusEnum().isRunning() ?
                                        v.getRunStatusEnum().getMessage() :
                                        v.getPrimeValueList().stream().map(String::valueOf).collect(Collectors.joining(","))));
                    });
                } else if (inputNumber.equals("0")) {
                    return;
                } else {
                    CalcPrimeValueStatus calcPrimeValueStatus = new CalcPrimeValueStatus(Integer.parseInt(inputNumber));
                    inputMap.put(++key, calcPrimeValueStatus);
                    CalcPrimaryValueThread thread = new CalcPrimaryValueThread(inputMap, key, calcPrimeValueStatus);
                    thread.start();
                }

            }
        } catch (IOException e) {
            System.out.println("input failed. message=" + e.getMessage());
            return;
        }
    }

    public static class CalcPrimaryValueThread extends Thread {
        private Map<Integer, CalcPrimeValueStatus> inputMap;
        private int targetKey;
        private CalcPrimeValueStatus calcPrimeValueStatus;

        public CalcPrimaryValueThread(Map<Integer, CalcPrimeValueStatus> inputMap, int targetKey, CalcPrimeValueStatus calcPrimeValueStatus) {
            this.inputMap = inputMap;
            this.targetKey = targetKey;
            this.calcPrimeValueStatus = calcPrimeValueStatus;
        }

        public void run() {

            if (calcPrimeValueStatus.getTargetValue() % 2 == 0) {
                calcPrimeValueStatus.addPrimeValue(2);
            }
            for (int i = 3; i <= calcPrimeValueStatus.getTargetValue(); i = i + 2) {
                if (calcPrimeValueStatus.getTargetValue() % i == 0) {
                    boolean isPrimeNumber = true;
                    for (int j = 3; j < i; j = j + 2) {
                        if (i % j == 0) {
                            isPrimeNumber = false;
                            continue;
                        }
                    }
                    if (isPrimeNumber) {
                        calcPrimeValueStatus.addPrimeValue(i);
                    }
                }
            }
            inputMap.get(targetKey).updateRunStatusEnum(RunStatusEnum.FINISHED);
        }
    }
}
// 完成までの時間: 1時間 50分