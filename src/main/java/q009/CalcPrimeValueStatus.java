package q009;

import java.util.ArrayList;
import java.util.List;

public class CalcPrimeValueStatus {
    /** 値 */
    private int targetValue;

    /** 素因数 */
    private List<Integer> primeValueList;

    private RunStatusEnum runStatusEnum;

    public CalcPrimeValueStatus(int value) {
        this.targetValue = value;
        this.primeValueList = new ArrayList<>();
        this.runStatusEnum = RunStatusEnum.RUNNING;
    }

    public int getTargetValue() {
        return targetValue;
    }

    public List<Integer> getPrimeValueList() {
        return primeValueList;
    }

    public RunStatusEnum getRunStatusEnum() {
        return runStatusEnum;
    }

    public void addPrimeValue(int value){
        primeValueList.add(value);
    }

    public void updateRunStatusEnum(RunStatusEnum runStatusEnum){
        this.runStatusEnum = runStatusEnum;
    }
}
