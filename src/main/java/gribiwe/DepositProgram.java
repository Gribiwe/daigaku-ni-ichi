package gribiwe;

public class DepositProgram {

    private int id;
    private float percent;
    private float additionalPercents;
    private ReplenishBehaviour replenishBehaviour;
    private WithdrawBehaviour withdrawBehaviour;

    public DepositProgram(int id, float percent, float additionalPercents, ReplenishBehaviour replenishBehaviour, WithdrawBehaviour withdrawBehaviour) {
        this.id = id;
        this.percent = percent;
        this.additionalPercents = additionalPercents;
        this.replenishBehaviour = replenishBehaviour;
        this.withdrawBehaviour = withdrawBehaviour;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public int getId() {
        return id;
    }

    public ReplenishBehaviour getReplenishBehaviour() {
        return replenishBehaviour;
    }

    public float getPercent() {
        return percent;
    }

    public float getAdditionalPercents() {
        return additionalPercents;
    }

    public ReplenishBehaviour isCanToReplenish() {
        return replenishBehaviour;
    }

    public WithdrawBehaviour getWithdrawBehaviour() {
        return withdrawBehaviour;
    }

    @Override
    public String toString() {
        return "DepositProgram{" +
                "id=" + id +
                ", percent=" + percent +
                ", additionalPercents=" + additionalPercents +
                ", replenishBehaviour=" + replenishBehaviour +
                ", withdrawBehaviour=" + withdrawBehaviour +
                '}';
    }
}
