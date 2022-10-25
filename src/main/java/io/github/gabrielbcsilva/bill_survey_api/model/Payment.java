package io.github.gabrielbcsilva.bill_survey_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
 
    private String bar_code;
    private String payment_date;
}
