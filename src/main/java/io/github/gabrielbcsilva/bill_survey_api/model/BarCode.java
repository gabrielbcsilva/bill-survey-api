package io.github.gabrielbcsilva.bill_survey_api.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BarCode {
    private String code;
    private String due_date;
    private String payment_date;
    private BigDecimal amount;
    private String recipient_name;
    private String recipient_document;
    private String type;


}
