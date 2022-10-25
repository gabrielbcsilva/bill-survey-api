package io.github.gabrielbcsilva.bill_survey_api.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    public static BigDecimal InterestAmountCalculate(LocalDate payment_Date, LocalDate due_Date, Double rate,
            BigDecimal amount) {
        try {
            if (payment_Date.isAfter(due_Date)) {
                int yearCalculate = (payment_Date.getYear() - due_Date.getYear());
                int dayCalculate = (payment_Date.getDayOfYear() - due_Date.getDayOfYear()) + ((yearCalculate * 365));
                int days = dayCalculate;
                double result = ((rate * days * amount.doubleValue()) / 100);

                return new BigDecimal(result).setScale(4, RoundingMode.HALF_EVEN);
            } else
                return new BigDecimal(0);
        } catch (Exception e) {
            return new BigDecimal(0);
        }

    }

    public static BigDecimal FineAmountCalculate(LocalDate payment_Date, LocalDate due_Date, BigDecimal amount,
            Double rate) {
        try {
            if (payment_Date.isAfter(due_Date)) {
                return new BigDecimal(amount.doubleValue() * rate).setScale(4, RoundingMode.HALF_EVEN);
            } else
                return new BigDecimal(0);
        } catch (Exception e) {
            return new BigDecimal(0);
        }

    }

    public static LocalDate parseDate(String date) {
        try {
            LocalDate due_date = LocalDate.parse(date);
            return due_date;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isJSONValid(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException e) {
			return false;
		}
	}

   



}
