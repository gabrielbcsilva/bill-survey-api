package io.github.gabrielbcsilva.bill_survey_api.test.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.gabrielbcsilva.bill_survey_api.model.BarCode;
import io.github.gabrielbcsilva.bill_survey_api.model.Bill;
import io.github.gabrielbcsilva.bill_survey_api.model.Payment;
import io.github.gabrielbcsilva.bill_survey_api.service.BillService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class BillControllerTest {
    
    static final String URL = "/bill-survey-api/bills";
    static final  int id=1;
    static final BigDecimal original_amount=new BigDecimal(100);
    static final BigDecimal amount=new BigDecimal(100);
    static final String due_date="2022-10-30";
    static final String payment_date="2022-10-30";
    static final BigDecimal interest_amount_calculated=new BigDecimal(0);
    static final BigDecimal fine_amount_calculated=new BigDecimal(0);
    static final String type = "NCP";

	
	HttpHeaders headers;

	@Autowired
	MockMvc mockMvc;

    @MockBean
	BillService billService;
	
	@BeforeAll
	private void setUp() {
		headers = new HttpHeaders();
       
	}

    @Test
	@Order(1)
	public void testCreate() throws Exception {
		
		BDDMockito.given(billService.create(Mockito.any(BarCode.class))).willReturn(getMockBill());
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL).content(getBillJsonPayload("34199800020104352008771020110004191070010000","2022-10-30"))
			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
			.headers(headers))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id").value(id))
		.andExpect(jsonPath("$.original_amount").value(original_amount))
		.andExpect(jsonPath("$.due_date").value(due_date))
		.andExpect(jsonPath("$.payment_date").value(payment_date))
		.andExpect(jsonPath("$.interest_amount_calculated").value(interest_amount_calculated))
		.andExpect(jsonPath("$.fine_amount_calculated").value(fine_amount_calculated))
		.andExpect(jsonPath("$.type").value(type))
        ;
		
	}


    private Bill getMockBill() throws ParseException {
       return  new Bill(1, new BigDecimal(100),  new BigDecimal(100), LocalDate.parse("2022-10-30"), LocalDate.parse("2022-10-30"), new BigDecimal(0), new BigDecimal(0),"NCP","34191790010104351004791020150008291070026000");
              
	}

    private String getBillJsonPayload(String bar_code,String payment_Date) throws JsonProcessingException {
		Payment payment = new Payment(bar_code,payment_Date);
		return new ObjectMapper().writeValueAsString(payment);
	}


}
