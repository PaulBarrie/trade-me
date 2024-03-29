package org.esgi.trademe.payment.application.retrieve;


import org.esgi.trademe.kernel.query.QueryHandler;
import org.esgi.trademe.payment.domain.Payment;
import org.esgi.trademe.payment.domain.PaymentRepository;

public final class RetrievePaymentHandler implements QueryHandler<RetrievePayment, Payment> {

    private final PaymentRepository paymentRepository;

    public RetrievePaymentHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment handle(RetrievePayment query) {
        return paymentRepository.findById(query.getPaymentID());
    }
}
