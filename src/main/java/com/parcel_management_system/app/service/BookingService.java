package com.parcel_management_system.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parcel_management_system.app.dto.request.BookingHistoryCustomerRequestDto;
import com.parcel_management_system.app.dto.request.BookingHistoryOfficerRequestDto;
import com.parcel_management_system.app.dto.request.BookingParcelRequestDto;
import com.parcel_management_system.app.dto.request.DropoffTimeRequestDto;
import com.parcel_management_system.app.dto.request.ParcelRequestDto;
import com.parcel_management_system.app.dto.request.PickupTimeRequestDto;
import com.parcel_management_system.app.dto.request.ReceiverDetailsRequestDto;
import com.parcel_management_system.app.dto.response.BookingHistoryCustomerResponseDto;
import com.parcel_management_system.app.dto.response.BookingHistoryOfficerResponseDto;
import com.parcel_management_system.app.dto.response.BookingResponseDto;
import com.parcel_management_system.app.dto.response.InvoiceDetailsResponseDto;
import com.parcel_management_system.app.dto.response.InvoicePaymentDetailsResponseDto;
import com.parcel_management_system.app.entity.Booking;
import com.parcel_management_system.app.entity.Parcel;
import com.parcel_management_system.app.entity.ReceiverDetails;
import com.parcel_management_system.app.entity.Traking;
import com.parcel_management_system.app.entity.User;
import com.parcel_management_system.app.enums.EBookingStatus;
import com.parcel_management_system.app.exception.CustomException;
import com.parcel_management_system.app.exception.ResourceNotFound;
import com.parcel_management_system.app.repository.BookingRepository;
import com.parcel_management_system.app.repository.ParcelRepository;
import com.parcel_management_system.app.repository.ReceiverDetailsRepository;
import com.parcel_management_system.app.repository.TrakingRepository;
import com.parcel_management_system.app.repository.UserRepository;
import com.parcel_management_system.app.utils.BookingUtil;
import com.parcel_management_system.app.dto.response.ParcelFeedbackResponseDto;
import com.parcel_management_system.app.entity.Feedback;
import com.parcel_management_system.app.repository.Feedbackrepository;
import java.util.Optional;

@Service
public class BookingService {
        @Autowired
        private BookingRepository bookingRepository;

        @Autowired
        private ReceiverDetailsRepository receiverDetailsRepository;

        @Autowired
        private Feedbackrepository feedbackrepository;

        @Autowired
        private ParcelRepository parcelRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private BookingUtil bookingUtil;

        @Autowired
        private TrakingRepository trakingRepository;

        @Autowired
        private PaymentService paymentService;

        @Transactional
        public BookingResponseDto createNewBookingByCustomer(BookingParcelRequestDto dto, Long officerId) {
                ReceiverDetailsRequestDto receiverDetailsDto = dto.getReceiverDetails();
                ParcelRequestDto parcelDto = dto.getParcel();

                User user = userRepository.findById(dto.getCustomerId())
                                .orElseThrow(() -> new ResourceNotFound("Customer"));

                ReceiverDetails receiverDetails = new ReceiverDetails();
                receiverDetails.setUser(user);
                receiverDetails.setEmail(receiverDetailsDto.getEmail());
                receiverDetails.setName(receiverDetailsDto.getName());
                receiverDetails.setMobileCountryCode(receiverDetailsDto.getMobileCountryCode());
                receiverDetails.setMobileNumber(receiverDetailsDto.getMobileNumber());
                receiverDetails.setAlternateMobileCountryCode(receiverDetailsDto.getAlternateMobileCountryCode());
                receiverDetails.setAlternateNumber(receiverDetailsDto.getAlternateNumber());
                receiverDetails.setHouseNo(receiverDetailsDto.getHouseNo());
                receiverDetails.setAddressLine1(receiverDetailsDto.getAddressLine1());
                receiverDetails.setAddressLine2(receiverDetailsDto.getAddressLine2());
                receiverDetails.setLandmark(receiverDetailsDto.getLandmark());
                receiverDetails.setCity(receiverDetailsDto.getCity());
                receiverDetails.setState(receiverDetailsDto.getState());
                receiverDetails.setPinCode(receiverDetailsDto.getPinCode());
                receiverDetails.setCountry(receiverDetails.getCountry());
                ReceiverDetails savedrReceiverDetails = receiverDetailsRepository.save(receiverDetails);

                Parcel parcel = new Parcel();
                parcel.setWeightInGrams(parcelDto.getWeightInGrams());
                parcel.setDescription(parcelDto.getDescription());
                parcel.setExpectedPickupTime(parcelDto.getExpectedPickupTime());
                parcel.setExpectedDropofTime(parcelDto.getExpectedDropofTime());
                parcel.setBaseRate(parcelDto.getBaseRate());
                parcel.setPackagingRate(parcelDto.getPackagingRate());
                parcel.setAdminFee(parcelDto.getAdminFee());
                parcel.setWeightCharge(parcelDto.getWeightCharge());
                parcel.setDeliveryCharge(parcelDto.getDeliveryCharge());
                parcel.setTaxAmount(parcelDto.getTaxAmount());
                parcel.setTotalCost(parcelDto.getTotalCost());
                Parcel savedParcel = parcelRepository.save(parcel);

                Booking booking = new Booking();
                booking.setPackagingType(dto.getPackagingType());
                booking.setDeliveryType(dto.getDeliveryType());
                booking.setTrakingId(bookingUtil.generateBookingCode());
                booking.setUser(user);
                booking.setReceiverDetails(savedrReceiverDetails);
                booking.setParcel(savedParcel);
                booking.setIsPaid(false);
                booking.setPayment(null);
                booking.setBookingStatus(EBookingStatus.BOOKED);
                booking.setDeliveryInstructions(dto.getDeliveryInstruction());
                booking.setOfficerId(officerId);
                Booking savedBooking = bookingRepository.save(booking);

                Traking traking = new Traking();
                traking.setBooking(savedBooking);
                traking.setBookingStatus(EBookingStatus.BOOKED);
                traking.setLocation("Parcel Booked");
                traking.setDescription(null);
                trakingRepository.save(traking);

                return new BookingResponseDto(
                                savedBooking.getTrakingId(),
                                savedBooking.getId());
        }

        public Page<BookingHistoryOfficerResponseDto> getBookingHistoryForOfficer(BookingHistoryOfficerRequestDto dto) {
                Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), Sort.by("createdAt").descending());

                LocalDateTime startDate = null;
                LocalDateTime endDate = null;

                if (dto.getDate() != null) {
                        startDate = dto.getDate().atStartOfDay();
                        endDate = dto.getDate().plusDays(1).atStartOfDay();
                }

                Page<Booking> bookings = bookingRepository.searchBookingOfficer(
                                dto.getBookingStatus(),
                                dto.getBookingKeyword(),
                                dto.getCustomerKeyword(),
                                startDate, endDate, pageable);

                return bookings.map(booking -> {
                        ParcelFeedbackResponseDto feedbackDto = null;
                        Optional<Feedback> feedbackOpt = feedbackrepository.findByBookingId(booking.getId());
                        if (feedbackOpt.isPresent()) {
                                Feedback f = feedbackOpt.get();
                                feedbackDto = new ParcelFeedbackResponseDto(f.getRating(), f.getSuggestions());
                        }

                        return new BookingHistoryOfficerResponseDto(
                                        booking.getId(),
                                        booking.getTrakingId(),
                                        booking.getUser().getUsername(),
                                        booking.getUser().getName(),
                                        booking.getCreatedAt(),
                                        booking.getReceiverDetails().getName(),
                                        booking.getReceiverDetails().getCity() + ", "
                                                        + booking.getReceiverDetails().getCountry(),
                                        booking.getParcel().getTotalCost(),
                                        booking.getBookingStatus(),
                                        booking.getIsPaid(),
                                        booking.getUser().getId(),
                                        feedbackDto);
                });
        }

        public Page<BookingHistoryCustomerResponseDto> getBookingHistoryForCustomer(
                        BookingHistoryCustomerRequestDto dto,
                        Long customerId) {
                Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), Sort.by("createdAt").descending());

                LocalDateTime startDate = null;
                LocalDateTime endDate = null;

                if (dto.getDate() != null) {
                        startDate = dto.getDate().atStartOfDay();
                        endDate = dto.getDate().plusDays(1).atStartOfDay();
                }

                Page<Booking> bookings = bookingRepository.searchBookingCustomer(
                                dto.getBookingStatus(),
                                dto.getBookingKeyword(),
                                customerId,
                                startDate, endDate, pageable);

                return bookings.map(booking -> new BookingHistoryCustomerResponseDto(
                                booking.getId(),
                                booking.getTrakingId(),
                                booking.getCreatedAt(),
                                booking.getReceiverDetails().getName(),
                                booking.getReceiverDetails().getCity() + ", "
                                                + booking.getReceiverDetails().getCountry(),
                                booking.getParcel().getTotalCost(),
                                booking.getBookingStatus(),
                                booking.getIsPaid()));
        }

        public InvoiceDetailsResponseDto getInvoiceDetails(Long bookingId) {
                Booking booking = bookingRepository.findById(bookingId)
                                .orElseThrow(() -> new ResourceNotFound("Booking"));

                if (booking.getPayment() == null) {
                        throw new CustomException("Payment not completed.", HttpStatus.BAD_REQUEST);
                }

                Parcel parcelDetails = booking.getParcel();
                ReceiverDetails receiverDetails = booking.getReceiverDetails();

                InvoicePaymentDetailsResponseDto invoicePaymentDetails = new InvoicePaymentDetailsResponseDto(
                                parcelDetails.getWeightInGrams(),
                                parcelDetails.getBaseRate(),
                                parcelDetails.getPackagingRate(),
                                parcelDetails.getAdminFee(),
                                parcelDetails.getWeightCharge(),
                                parcelDetails.getDeliveryCharge(),
                                parcelDetails.getTaxAmount(),
                                parcelDetails.getTotalCost());

                InvoiceDetailsResponseDto response = new InvoiceDetailsResponseDto(
                                booking.getTrakingId(),
                                booking.getUser().getUsername(),
                                booking.getCreatedAt(),
                                receiverDetails.getName(),
                                receiverDetails.getHouseNo() + ", " + receiverDetails.getAddressLine1() + ", "
                                                + receiverDetails.getCity(),
                                booking.getBookingStatus(),
                                booking.getPayment().getPaymentStatus(),
                                invoicePaymentDetails,
                                booking.getPayment().getTransactionId());

                return response;
        }

        public void setPickupTime(PickupTimeRequestDto dto, String bookingId) {
                Booking booking = bookingRepository.findByTrakingId(bookingId)
                                .orElseThrow(() -> new ResourceNotFound("Booking"));

                Parcel parcelDetails = booking.getParcel();
                if (parcelDetails.getActualPickupTime() != null) {
                        throw new CustomException("Parcel already picked up", HttpStatus.BAD_REQUEST);
                }

                parcelDetails.setActualPickupTime(dto.getPickupTime());
                parcelRepository.save(parcelDetails);

                booking.setBookingStatus(EBookingStatus.PICKED_UP);
                bookingRepository.save(booking);

                Traking traking = new Traking();
                traking.setBooking(booking);
                traking.setBookingStatus(EBookingStatus.PICKED_UP);
                traking.setLocation("Parcel Picked Up");
                traking.setDescription(null);
                trakingRepository.save(traking);
        }

        public void setDropoffTime(DropoffTimeRequestDto dto, String bookingId) {
                Booking booking = bookingRepository.findByTrakingId(bookingId)
                                .orElseThrow(() -> new ResourceNotFound("Booking"));

                if (booking.getPayment() == null) {
                        throw new CustomException("Parcel payment must be done before delivery",
                                        HttpStatus.BAD_REQUEST);
                }

                Parcel parcelDetails = booking.getParcel();
                if (parcelDetails.getActualPickupTime() != null) {
                        throw new CustomException("Parcel already picked up", HttpStatus.BAD_REQUEST);
                }

                parcelDetails.setActualDropofTime(dto.getDropoffTime());
                parcelRepository.save(parcelDetails);

                booking.setBookingStatus(EBookingStatus.DELIVERED);
                bookingRepository.save(booking);

                Traking traking = new Traking();
                traking.setBooking(booking);
                traking.setBookingStatus(EBookingStatus.DELIVERED);
                traking.setLocation(booking.getReceiverDetails().getAddressLine1() + ", "
                                + booking.getReceiverDetails().getCity());
                traking.setDescription(null);
                trakingRepository.save(traking);
        }

        public void parcelInTransit(String bookingId) {
                Booking booking = bookingRepository.findByTrakingId(bookingId)
                                .orElseThrow(() -> new ResourceNotFound("Booking"));

                booking.setBookingStatus(EBookingStatus.IN_TRANSIT);
                bookingRepository.save(booking);

                Traking traking = new Traking();
                traking.setBooking(booking);
                traking.setBookingStatus(EBookingStatus.IN_TRANSIT);
                traking.setLocation("Mumbai");
                traking.setDescription(null);
                trakingRepository.save(traking);
        }

        public void outForDelivery(String bookingId) {
                Booking booking = bookingRepository.findByTrakingId(bookingId)
                                .orElseThrow(() -> new ResourceNotFound("Booking"));

                booking.setBookingStatus(EBookingStatus.OUT_FOR_DELIVERY);
                bookingRepository.save(booking);

                Traking traking = new Traking();
                traking.setBooking(booking);
                traking.setBookingStatus(EBookingStatus.OUT_FOR_DELIVERY);
                traking.setLocation(booking.getReceiverDetails().getCity());
                traking.setDescription(null);
                trakingRepository.save(traking);
        }

        public void cancelOrder(String bookingId) {
                Booking booking = bookingRepository.findByTrakingId(bookingId)
                                .orElseThrow(() -> new ResourceNotFound("Booking"));

                if (booking.getBookingStatus() == EBookingStatus.DELIVERED) {
                        throw new CustomException("Parcel already delivered", HttpStatus.BAD_REQUEST);
                }

                if (booking.getPayment() != null) {
                        paymentService.refundAmount(bookingId);
                }

                booking.setBookingStatus(EBookingStatus.CANCELLED);
                bookingRepository.save(booking);

                Traking traking = new Traking();
                traking.setBooking(booking);
                traking.setBookingStatus(EBookingStatus.CANCELLED);
                traking.setLocation(booking.getReceiverDetails().getCity());
                traking.setDescription(null);
                trakingRepository.save(traking);
        }

        public void failedDelivery(String bookingId) {
                Booking booking = bookingRepository.findByTrakingId(bookingId)
                                .orElseThrow(() -> new ResourceNotFound("Booking"));

                if (booking.getBookingStatus() == EBookingStatus.DELIVERED) {
                        throw new CustomException("Parcel already delivered", HttpStatus.BAD_REQUEST);
                }

                if (booking.getPayment() != null) {
                        paymentService.refundAmount(bookingId);
                }

                booking.setBookingStatus(EBookingStatus.FAILED_DELIVERY);
                bookingRepository.save(booking);

                Traking traking = new Traking();
                traking.setBooking(booking);
                traking.setBookingStatus(EBookingStatus.FAILED_DELIVERY);
                traking.setLocation(booking.getReceiverDetails().getCity());
                traking.setDescription(null);
                trakingRepository.save(traking);
        }

}
