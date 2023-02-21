package com.arcurus.organisationlist.repostory;

import com.arcurus.organisationlist.model.PaymentOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentOption,Long> {
    PaymentOption findPaymentOptionByBankDetails(String bankDetails);
}
