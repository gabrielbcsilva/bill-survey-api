package io.github.gabrielbcsilva.bill_survey_api.test.repository;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.gabrielbcsilva.bill_survey_api.model.Bill;
import io.github.gabrielbcsilva.bill_survey_api.repositories.BillRepo;

@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@TestInstance(Lifecycle.PER_CLASS)
public class BillRepoTest {
    @Autowired
	BillRepo repository;
	
	Bill bill;

    @Test
	public void testSave() {
		
		Bill bill = new Bill(1, new BigDecimal(100),  new BigDecimal(100), LocalDate.parse("2022-10-30"), LocalDate.parse("2022-10-30"), new BigDecimal(0), new BigDecimal(0),"NCP","34191790010104351004791020150008291070026000");
                Bill response = repository.save(bill);
		
		assertNotNull(response);
	}

    @AfterAll
	private void tearDown() {
		repository.deleteAll();
	}

}
