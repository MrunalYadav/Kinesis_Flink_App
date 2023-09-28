package com.proofpoint.models;

import java.sql.Timestamp;

public class ExchangeRate {
        public Timestamp exchangeRateTime;
        public String currency;
        public int rate;

        @Override
        public String toString() {
                return "ExchangeRate{" +
                        "exchangeRateTime=" + exchangeRateTime +
                        ", currency='" + currency + '\'' +
                        ", rate=" + rate +
                        '}';
        }
}
