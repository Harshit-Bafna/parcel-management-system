package com.parcel_management_system.app.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.parcel_management_system.app.dto.application.MockCard;
import com.parcel_management_system.app.enums.EPaymentMethod;
import com.parcel_management_system.app.repository.PaymentRepository;

@Component
public class PaymentUtil {
    @Autowired
    private PaymentRepository paymentRepository;

    private static final Set<MockCard> VALID_CARDS = new HashSet<>();

    static {
        VALID_CARDS.add(new MockCard(
                EPaymentMethod.DEBIT_CARD,
                "Visa",
                "1234",
                "Rahul Sharma"));

        VALID_CARDS.add(new MockCard(
                EPaymentMethod.DEBIT_CARD,
                "MasterCard",
                "5678",
                "Amit Verma"));

        VALID_CARDS.add(new MockCard(
                EPaymentMethod.CREDIT_CARD,
                "Visa",
                "4321",
                "Neha Singh"));

        VALID_CARDS.add(new MockCard(
                EPaymentMethod.CREDIT_CARD,
                "MasterCard",
                "8765",
                "Priya Mehta"));
    }

    public String generateTransactionId() {
        Random random = new Random();
        String transactionId;

        do {
            int randomNumber = 100000 + random.nextInt(900000);
            transactionId = "PAY-" + randomNumber;
        } while (paymentRepository.existsByTransactionId(transactionId));

        return transactionId;
    }

    public boolean isValidCard(
            EPaymentMethod paymentMethod,
            String cardBrand,
            String last4digits,
            String cardHolderName) {

        return VALID_CARDS.contains(
                new MockCard(
                        paymentMethod,
                        cardBrand,
                        last4digits,
                        cardHolderName
                )
        );
    }
}
