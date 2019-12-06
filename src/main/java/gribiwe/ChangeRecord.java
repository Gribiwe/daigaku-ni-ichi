package gribiwe;

public class ChangeRecord {
    
    private Long month;
    private String cash;

    public ChangeRecord(Long month, String cash) {
        this.month = month;
        this.cash = cash;
    }

    public void setMonth(Long month) {
        this.month = month;
    }

    public void setCash(String  cash) {
        this.cash = cash;
    }

    public Long getMonth() {
        return month;
    }

    public String getCash() {
        return cash;
    }
}
