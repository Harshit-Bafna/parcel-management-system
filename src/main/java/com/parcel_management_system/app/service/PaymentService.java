package com.parcel_management_system.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parcel_management_system.app.entity.Payment;
import com.parcel_management_system.app.enums.EPaymentStatus;
import com.parcel_management_system.app.dto.request.ParcelPaymentRequestDto;
import com.parcel_management_system.app.entity.Booking;
import com.parcel_management_system.app.exception.CustomException;
import com.parcel_management_system.app.exception.ResourceNotFound;
import com.parcel_management_system.app.repository.BookingRepository;
import com.parcel_management_system.app.repository.PaymentRepository;
import com.parcel_management_system.app.utils.PaymentUtil;

@Service
public class PaymentService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentUtil paymentUtil;

    @Transactional
    public void paymentForParcel(ParcelPaymentRequestDto dto) {
        Booking booking = bookingRepository.findByTrakingId(dto.getBookingId())
                .orElseThrow(() -> new ResourceNotFound("Booking"));

        String transactionId = paymentUtil.generateTransactionId();

        if (booking.getPayment() != null) {
            throw new CustomException("Payment already done.", HttpStatus.BAD_REQUEST);
        }

        boolean isValidCard = paymentUtil.isValidCard(
                dto.getPaymentMethod(),
                dto.getCardBrand(),
                dto.getLast4digits(),
                dto.getCardHolderName());

        if (!isValidCard) {
            throw new CustomException("Invalid card details.", HttpStatus.BAD_REQUEST);
        }

        Payment payment = new Payment();
        payment.setTransactionId(transactionId);
        payment.setAmount(booking.getParcel().getTotalCost());
        payment.setPaymentStatus(EPaymentStatus.PAID);
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setLast4digits(dto.getLast4digits());
        payment.setCardBrand(dto.getCardBrand());
        payment.setCardHolderName(dto.getCardHolderName());
        payment.setIsRefund(false);
        payment.setPaidAt(LocalDateTime.now());
        payment.setExpiryDate(dto.getExpiryDate());
        Payment savedPayment = paymentRepository.save(payment);

        booking.setIsPaid(true);
        booking.setPayment(savedPayment);
        bookingRepository.save(booking);
    }

    public void refundAmount(String bookingId) {
        Booking booking = bookingRepository.findByTrakingId(bookingId)
                .orElseThrow(() -> new ResourceNotFound("Booking"));

        Payment paymentDetails = booking.getPayment();
        paymentDetails.setIsRefund(true);
        paymentDetails.setRefundAmount(paymentDetails.getAmount());
        paymentDetails.setRefundedAt(LocalDateTime.now());
        paymentDetails.setPaymentStatus(EPaymentStatus.REFUND);
        paymentRepository.save(paymentDetails);
    }
}
