package org.camp;

import java.util.ArrayList;
import java.util.List;

public class Subscriber {
    public String phoneNumber;
    List<Call> calls;
    public String tariff;
    public int availableMinutes;

    Subscriber(String callType, String subscribersNumber, String callStartTime, String callEndTime, String tariff) {
        this.phoneNumber = subscribersNumber;
        calls = new ArrayList<>();
        calls.add(new Call(callType, callStartTime, callEndTime));
        this.tariff = tariff;
    }

    public void addCall (String callType, String callStartTime, String callEndTime) {

    }
}
