package io.github.gabrielbcsilva.bill_survey_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.gabrielbcsilva.bill_survey_api.model.Payment;

public class testEntry {
    public static void main(String[] args) throws JsonProcessingException {
        System.out.println(getJsonPayload("34199800020104352008771020110004191070010000","2022-10-30"));
    }
    private static String getJsonPayload(String bar_code,String payment_Date) throws JsonProcessingException  {
		Payment payment = new Payment(bar_code,payment_Date);
		return new ObjectMapper().writeValueAsString(payment);
	}
}
