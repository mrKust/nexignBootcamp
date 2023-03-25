package org.camp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Call {

    private final String callType;
    private final Date startedTime;
    private final Date finishedTime;
    private double callCost;
    private final long callDurationInMillis;

    Call(String callType, String callStartTime, String callEndTime) {
        this.callType = callType;
        this.callCost = 0;
        DateFormat format = new SimpleDateFormat( "yyyyMMddHHmmss" );
        try {
            this.startedTime = format.parse(callStartTime);
            this.finishedTime = format.parse(callEndTime);
            this.callDurationInMillis = this.finishedTime.getTime() - this.startedTime.getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCallType() {
        return callType;
    }

    public Date getStartedTime() {
        return startedTime;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public double getCallCost() {
        return callCost;
    }

    public long getCallDurationInMillis() {
        return callDurationInMillis;
    }

    public void setCallCost(double callCost) {
        this.callCost = callCost;
    }
}