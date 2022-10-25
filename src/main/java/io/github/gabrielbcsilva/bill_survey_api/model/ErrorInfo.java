package io.github.gabrielbcsilva.bill_survey_api.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo {
    private Date timestamp;
    private int status;
    private String error;
    private String path; 
}
