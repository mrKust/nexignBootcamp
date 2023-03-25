package org.camp;

import java.util.ArrayList;
import java.util.List;

public class Subscriber {
    private List<Call> calls;
    private final String tariff;
    private long availableIncludedMinutesInMillis;
    private double moneyAmount;

    Subscriber(Call newCall, String tariff) {
        calls = new ArrayList<>();
        this.tariff = tariff;
        switch (this.tariff) {
            case ("06"):
                this.availableIncludedMinutesInMillis = 300 * 60 * 1000;
                this.moneyAmount += 100.0;
                break;
            case ("03"):
                break;
            case ("11"):
                this.availableIncludedMinutesInMillis = 100 * 60 * 1000;
                break;
        }
        this.addCall(newCall);
    }

    public void addCall (Call newCall) {
        calls.add(newCall);
        switch (this.tariff) {
            case ("06"):
                newCall.setCallCost(countCost(newCall.getCallDurationInMillis(), 1, 0));
                break;
            case ("03"):
                newCall.setCallCost(tarifficateByMinutes(newCall.getCallDurationInMillis(), 1.5));
                break;
            case ("11"):
                if (newCall.getCallType().equals("02")) {
                    newCall.setCallCost(0);
                } else {
                    newCall.setCallCost(countCost(newCall.getCallDurationInMillis(), 1.5, 0.5));
                }
                break;
        }
    }

    private double tarifficateByMinutes(long durationTime, double tariffPrice) {
        return (durationTime / 1000 / 60) * tariffPrice;
    }

    private double countCost(long durationTime, double tariffPrice, double tariffPricePrivileged) {
        double result = 0;
        if (availableIncludedMinutesInMillis == 0) {
            long numberOfMinutes = durationTime / 1000 / 60;
            result = numberOfMinutes * tariffPrice;
        } else if (availableIncludedMinutesInMillis > 0) {
            if (availableIncludedMinutesInMillis >= durationTime) {
                availableIncludedMinutesInMillis -= durationTime;
                result = tarifficateByMinutes(durationTime, tariffPricePrivileged);
            } else {
                long diff = Math.abs(availableIncludedMinutesInMillis - durationTime);
                availableIncludedMinutesInMillis = 0;
                result = tarifficateByMinutes(diff, tariffPrice);
            }
        }
        return result;
    }

    public List<Call> getCalls() {
        return calls;
    }

    public String getTariff() {
        return tariff;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }
}
