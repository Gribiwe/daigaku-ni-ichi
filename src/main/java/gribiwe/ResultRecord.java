package gribiwe;

public class ResultRecord {

    private Long month;
    private Double cash;

    public ResultRecord(Long month, Double cash) {
        this.month = month;
        this.cash = cash;
    }

    public void setMonth(Long month) {
        this.month = month;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Long getMonth() {
        return month;
    }

    public Double getCash() {
        return cash;
    }
}
