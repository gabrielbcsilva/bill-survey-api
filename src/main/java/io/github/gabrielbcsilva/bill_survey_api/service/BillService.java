package io.github.gabrielbcsilva.bill_survey_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import io.github.gabrielbcsilva.bill_survey_api.model.Bill;
import io.github.gabrielbcsilva.bill_survey_api.repositories.BillRepo;
import io.github.gabrielbcsilva.bill_survey_api.utils.Util;
import io.github.gabrielbcsilva.bill_survey_api.model.BarCode;

@Service
public class BillService {
	RestTemplateBuilder restTBuilder = new RestTemplateBuilder();
	BillRepo billRepo;
	
	@Autowired
	public BillService(BillRepo billRepo) {
		this.billRepo = billRepo;
	}

	private void setBillValues(Bill bill, BarCode paymentInfo) {

		bill.setOriginal_amount(paymentInfo.getAmount());
		bill.setDue_date(Util.parseDate(paymentInfo.getDue_date()));
		bill.setPayment_date(Util.parseDate(paymentInfo.getPayment_date()));
		bill.setType(paymentInfo.getType());
		bill.setFine_amount_calculated(
				Util.FineAmountCalculate(bill.getPayment_date(), bill.getDue_date(), paymentInfo.getAmount(), 0.02));
		bill.setInterest_amount_calculated(Util.InterestAmountCalculate(bill.getPayment_date(), bill.getDue_date(),
				0.033, paymentInfo.getAmount()));
		bill.setAmount(bill.getOriginal_amount()
				.add(bill.getInterest_amount_calculated().add(bill.getFine_amount_calculated())));
		bill.setBar_code(paymentInfo.getCode());

	}

	public Bill create(BarCode paymentInfo) {

		Bill bill = new Bill();
		setBillValues(bill, paymentInfo);
		
		return billRepo.save(bill);
	}
}
