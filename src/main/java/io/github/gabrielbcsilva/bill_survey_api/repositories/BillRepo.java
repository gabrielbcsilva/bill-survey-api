package io.github.gabrielbcsilva.bill_survey_api.repositories;

import org.springframework.data.repository.CrudRepository;

import io.github.gabrielbcsilva.bill_survey_api.model.Bill;

public interface BillRepo extends CrudRepository<Bill, Integer>  {
    
}
