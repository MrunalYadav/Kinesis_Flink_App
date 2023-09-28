package com.proofpoint.models;

import java.sql.Timestamp;

public class Order {

        public int id;
        public Timestamp orderTime;
        public int amount;
        public String currency;

        @Override
        public String toString() {
                return "Order{" +
                        "id=" + id +
                        ", orderTime=" + orderTime +
                        ", amount=" + amount +
                        ", currency='" + currency + '\'' +
                        '}';
        }
}
