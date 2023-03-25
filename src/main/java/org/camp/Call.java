package org.camp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Call {

    public String callType;
    public Date startedTime;
    public Date finishedTime;

    public double callCost;

    Call(String callType, String callStartTime, String callEndTime) {
        this.callType = callType;
        this.callCost = 0;
        DateFormat format = new SimpleDateFormat( "yyyyMMddHHmmss" );
        try {
            this.startedTime = format.parse(callStartTime);
            this.finishedTime = format.parse(callEndTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
