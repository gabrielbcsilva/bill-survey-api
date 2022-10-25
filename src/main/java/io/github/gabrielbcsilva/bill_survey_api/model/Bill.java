package io.github.gabrielbcsilva.bill_survey_api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_payment_transactions")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "AMOUNTORIG",  nullable = true)
    private BigDecimal original_amount;
    @Column(name = "AMOUNT",  nullable = true)
    private BigDecimal amount;
    @Column(name = "DUEDATE",  nullable = true)
    private LocalDate due_date;
    @Column(name = "PAYMENTDATE",  nullable = true)
    private LocalDate payment_date;
    @Column(name = "AMOUNTINT",  nullable = false)
    private BigDecimal interest_amount_calculated;
    @Column(name = "AMOUNTFINE",  nullable = false)
    private BigDecimal fine_amount_calculated;
    @Column(name = "TYPE",  nullable = false)
    private String type;
    @Column(name = "BARCODE",  nullable = false)
    private String bar_code;

   
}
