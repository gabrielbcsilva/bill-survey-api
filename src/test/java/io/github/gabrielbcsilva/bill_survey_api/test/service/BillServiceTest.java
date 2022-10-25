package io.github.gabrielbcsilva.bill_survey_api.test.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.gabrielbcsilva.bill_survey_api.model.BarCode;
import io.github.gabrielbcsilva.bill_survey_api.model.Bill;
import io.github.gabrielbcsilva.bill_survey_api.repositories.BillRepo;
import io.github.gabrielbcsilva.bill_survey_api.service.BillService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class BillServiceTest {
    @Autowired
	private BillService service;

	@MockBean
	private BillRepo repository;

    @Test
	@Order(1)
	public void testSave() {
		
		BDDMockito.given(repository.save(Mockito.any(Bill.class)))
			.willReturn(getMockBill());
          Bill response = service.create(new BarCode("34191790010104351004791020150008291070026000","2022-09-03","2022-09-03",new BigDecimal(100),"Microhouse Informática S/C Ltda", "83698283000114","NPC"));
		
		assertNotNull(response);
	}
	private Bill getMockBill() {
		return new Bill(1, new BigDecimal(100),  new BigDecimal(100), LocalDate.parse("2022-10-30"), LocalDate.parse("2022-10-30"), new BigDecimal(0), new BigDecimal(0),"NCP","34191790010104351004791020150008291070026000");
	}

    @AfterAll
	private void tearDown() {
		repository.deleteAll();
	}
	
}
