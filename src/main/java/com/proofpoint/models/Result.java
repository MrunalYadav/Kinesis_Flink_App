package com.proofpoint.models;

import java.sql.Timestamp;
public class Result {
    public int id;
    public String orderTime ;
    public int originalAmount;
    public int convertedAmount;

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", orderTime='" + orderTime + '\'' +
                ", originalAmount=" + originalAmount +
                ", convertedAmount=" + convertedAmount +
                '}';
    }

    public Result(int id, String orderTime, int originalAmount, int convertedAmount) {
        this.id = id;
        this.orderTime = orderTime;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
    }
}