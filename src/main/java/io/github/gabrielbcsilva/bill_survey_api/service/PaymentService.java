package io.github.gabrielbcsilva.bill_survey_api.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import io.github.gabrielbcsilva.bill_survey_api.model.Payment;

@Service
public class PaymentService {
   
    public static boolean isDateValid(String dateS) {
		try {
			LocalDate date = LocalDate.parse(dateS);
			return date != null;
		} catch (Exception e) {
			return false;
		}
	}

    public static boolean isEmpty(Payment paymentRequest) {
        if(paymentRequest.getBar_code() == null || paymentRequest.getPayment_date() == null
        || paymentRequest.getBar_code().isEmpty() == true
        || paymentRequest.getPayment_date().isEmpty() == true){
            return true;
        }
        return false;
    }
}
