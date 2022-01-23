package org.esgi.trademe.payment.exposition;

import org.esgi.trademe.kernel.command.CommandBus;
import org.esgi.trademe.kernel.exceptions.InvalidEntryException;
import org.esgi.trademe.kernel.exceptions.PaymentRejectedException;
import org.esgi.trademe.kernel.query.QueryBus;
import org.esgi.trademe.contractor.application.retrieve.all.RetrieveContractors;
import org.esgi.trademe.contractor.domain.ContractorID;
import org.esgi.trademe.contractor.exposition.ContractorDTO;
import org.esgi.trademe.payment.application.create.CreatePayment;
import org.esgi.trademe.payment.application.retrieve.RetrievePaymentsByContractorID;
import org.esgi.trademe.payment.application.update.UsePayment;
import org.esgi.trademe.payment.domain.AccountIdentityPayment;
import org.esgi.trademe.payment.domain.CreditCardPayment;
import org.esgi.trademe.payment.domain.Payment;
import org.esgi.trademe.payment.domain.SubscriptionDetails;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@RestController
public final class PaymentController {

    private final QueryBus queryBus;
    private final CommandBus commandBus;
    private final SubscriptionDetails subscriptionDetails;


    public PaymentController(QueryBus queryBus, CommandBus commandBus, SubscriptionDetails subscriptionDetails) {
        this.queryBus = queryBus;
        this.commandBus = commandBus;
        this.subscriptionDetails = subscriptionDetails;
    }

    @PostMapping(value="/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentDTO> addPayment(@RequestParam(required = true)  int contractorID,
                                                 @RequestParam(required = true) Map<String, String> paymentDetails) throws InvalidEntryException, NoSuchAlgorithmException {

        if (paymentDetails.containsKey("owner") && paymentDetails.containsKey("number") && paymentDetails.containsKey("expiration")
        && paymentDetails.containsKey("security_number")) {
            // Then it's credit card
            final Payment payment = commandBus.send(CreatePayment.of(ContractorID.of(contractorID), CreditCardPayment.of(
                    paymentDetails.get("owner"), paymentDetails.get("number"), paymentDetails.get("expiration"),
                    paymentDetails.get("security_number"))
            ));
            PaymentDTO paymentDTO = PaymentDTO.of(payment);
            return ResponseEntity.ok(paymentDTO);
        } else if(paymentDetails.containsKey("account_identifier") && paymentDetails.containsKey("bank_identifier")) {
            // Then it's Account Identity
            final Payment payment = commandBus.send(CreatePayment.of(ContractorID.of(contractorID), AccountIdentityPayment.of(
                    paymentDetails.get("account_identifier"), paymentDetails.get("bank_identifier"))));
            PaymentDTO paymentDTO = PaymentDTO.of(payment);
            return ResponseEntity.ok(paymentDTO);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/payments", produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentsDTO> getAll() {
//        final List<PaymentDTO> contractors = queryBus.send(new RetrievePayment(paymentID));
        PaymentsDTO contractorsDTOResult = new PaymentsDTO();
        contractorsDTOResult.contractors = null;
        return ResponseEntity.ok(contractorsDTOResult);
    }

    //Every Month on the last Friday
    @Scheduled(cron = "* * * * * *", zone = "Europe/Paris")
    public void paySubscription() throws InvalidEntryException, NoSuchAlgorithmException {
        List<ContractorDTO> contractors = queryBus.send(new RetrieveContractors());
        for(ContractorDTO contractor: contractors) {
            List<Payment> payments = queryBus.send(new RetrievePaymentsByContractorID(contractor.getId()));
            if(payments.size() == 0) break;
            Payment payment = payments.get(0);
            Boolean response = commandBus.send(new UsePayment(payment.getPaymentMode(), subscriptionDetails.getPaymentMode(),
                    subscriptionDetails.getMonthlyAmount()));
            System.out.println(String.format("%s %s paid subscription", contractor.getFirstname(), contractor.getLastname()));

            if(!response) {
                throw new PaymentRejectedException(String.format("Contractor %s can't pay subscription with payment %s",
                        contractor.getId().toString(), payment.getId().toString()));
            }
        }
    }

//    @Scheduled(cron = "0 0 12 6L * ?", zone = "Europe/Paris")
//    public void payTradesman() {
//        List<Project> projects = queryBus.send(new RetrieveProjectByStatus(ProjectStatus.ACCEPTED));
//        for(Project project: projects) {
//            Payment fromPayment =queryBus.send(new RetrievePaymentsByContractorID(project.getContractorID()));
//            Payment toPayment =queryBus.send(new RetrievePaymentsByContractorID(project.getContractorID()));
//            Boolean response = commandBus.send(new UsePayment(payment, null, 10F));
//            if(!response) {
//                throw new PaymentRejectedException(String.format("Contractor %s can't pay subscription with payment %s",
//                        contractor.getId().toString(), payment.getId().toString()));
//            }
//        }
//    }

}
