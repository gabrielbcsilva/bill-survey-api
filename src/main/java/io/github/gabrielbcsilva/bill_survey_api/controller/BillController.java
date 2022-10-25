package io.github.gabrielbcsilva.bill_survey_api.controller;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.gabrielbcsilva.bill_survey_api.enumeration.BarCodeTypeEnum;
import io.github.gabrielbcsilva.bill_survey_api.model.Auth;
import io.github.gabrielbcsilva.bill_survey_api.model.Bill;
import io.github.gabrielbcsilva.bill_survey_api.model.ErrorInfo;
import io.github.gabrielbcsilva.bill_survey_api.model.Payment;
import io.github.gabrielbcsilva.bill_survey_api.model.BarCode;
import io.github.gabrielbcsilva.bill_survey_api.service.BillService;
import io.github.gabrielbcsilva.bill_survey_api.service.PaymentService;

@RestController
@RequestMapping("/bill-survey-api/bills")
public class BillController {
	private static final Logger logger = Logger.getLogger(BillController.class);
	private final RestTemplate restTemplate = new RestTemplateBuilder().build();

	@Autowired
	private BillService billService;

	@PostMapping
	@ResponseBody
	public ResponseEntity<Object> create(@RequestBody(required = true) Payment paymentRequest) {
		String path = "/bill-survey-api/bills";

		var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("").build().toUri();
		try {
			if (PaymentService.isEmpty(paymentRequest)) {
				return ResponseEntity.badRequest().body(new ErrorInfo(new Date(), HttpStatus.BAD_REQUEST.value(),
						"Não são permitidos valores nulos ou campos vazios.", path));
			}

			if (!PaymentService.isDateValid(paymentRequest.getPayment_date())) {
				return ResponseEntity.unprocessableEntity().body(new ErrorInfo(new Date(),
						HttpStatus.UNPROCESSABLE_ENTITY.value(), "Data inválida. Tipo permitido: YYYY-MM-DD", path));
			}

			BarCode barCode = info(paymentRequest);
			
			if (BarCodeTypeEnum.getEnum(barCode.getType()) != BarCodeTypeEnum.NCP) {
				return ResponseEntity.unprocessableEntity().body(new ErrorInfo(new Date(),
						HttpStatus.UNPROCESSABLE_ENTITY.value(), "Tipo não permitido, apenas boletos NPC", path));
			}
			
			Bill billCreated = billService.create(barCode);

			
			return ResponseEntity.created(uri).body(billCreated);

		} catch (HttpClientErrorException e) {
			return e.getStatusCode().value() == 404 ? ResponseEntity.notFound().build()
					: ResponseEntity.internalServerError().build();
		}

	}

	public BarCode info(Payment paymentRequest) {
		try {
			String token = gerarToken().getToken();

			String url = "https://vagas.builders/api/builders/bill-payments/codes";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add("Authorization", token);

			Map<String, Object> map = new HashMap<>();
			map.put("code", paymentRequest.getBar_code());

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
			ResponseEntity<BarCode> response = null;

			response = this.restTemplate.postForEntity(url, entity, BarCode.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				BarCode payment = response.getBody();
				if (payment != null)
					payment.setPayment_date(paymentRequest.getPayment_date());
				return payment;
			} else {
				return null;
			}

		} catch (HttpClientErrorException e) {
			String erro = e.toString();
			logger.info(erro);
			if (erro.contains("404"))
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
			else
				throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private Auth gerarToken() {
		String url = "https://vagas.builders/api/builders/auth/tokens";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		Map<String, Object> map = new HashMap<>();
		map.put("client_id", "bd753592-cf9b-4d1a-96b9-cb8b2c01bd12");
		map.put("client_secret", "4e8229fe-1131-439c-9846-799895a8be5b");

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

		ResponseEntity<Auth> response = this.restTemplate.postForEntity(url, entity, Auth.class);

		if (response.getStatusCode() == HttpStatus.CREATED) {
			return response.getBody();
		} else {
			return null;
		}
	}
}
