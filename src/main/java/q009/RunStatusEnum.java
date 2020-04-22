package q009;

public enum RunStatusEnum {
    RUNNING("running", "実行中"),
    FINISHED("finished", "終了");

    private String label;
    private String message;

    RunStatusEnum(String label, String message) {
        this.label = label;
        this.message = message;
    }

    public boolean isRunning(){
        return RUNNING.label.equals(this.label);
    }

    public String getLabel() {
        return label;
    }

    public String getMessage() {
        return message;
    }
}
