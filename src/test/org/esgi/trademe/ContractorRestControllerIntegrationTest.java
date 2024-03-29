package org.esgi.trademe;

import org.esgi.trademe.kernel.hash.SHA256Engine;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContractorRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateValidContractor() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/contractor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("first_name", "Paul")
                        .param("last_name", "Barrié")
                        .param("email", "paul@gmail.com")
                        .param("birth", "31/10/1995")
                        .param("street_number", "2")
                        .param("street_name", "rue de la Paix")
                        .param("zip_code", "75002")
                        .param("city", "Paris")
                        .param("country", "FRANCE")
                        .param("username", "paulb")
                        .param("password", "P@55w0rd"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id.value").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("Paul"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("Barrié"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("paul@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birth").value("31/10/1995"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.credentials.username").value("paulb"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.credentials.password").value(new SHA256Engine().encrypt("P@55w0rd")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.streetNumber").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.streetName").value("rue de la Paix"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.zipCode").value("75002"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.city").value("Paris"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.country").value("FRANCE"));
    }

    @Test
    public void testCreateContractorWithInvalidEmail() throws Exception {
        String expected = "paul@gmail is not a valid value for the field Email";
        String error = this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/contractor")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("first_name", "Paul")
                                .param("last_name", "Barrié")
                                .param("email", "paul@gmail")
                                .param("birth", "31/10/1995")
                                .param("street_number", "2")
                                .param("street_name", "rue de la Paix")
                                .param("zip_code", "75002")
                                .param("city", "Paris")
                                .param("country", "FRANCE")
                                .param("username", "paulb")
                                .param("password", "P@55w0rd"))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andReturn().getResolvedException().getMessage();
        Assert.assertEquals(error, expected);
    }

    @Test
    public void testCreateContractorWithInvalidFirstname() throws Exception {
        String expected = "P@ul is not a valid value for the field Firstname";
        String error = this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/contractor")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("first_name", "P@ul")
                                .param("last_name", "Barrié")
                                .param("email", "paul@gmail.com")
                                .param("birth", "10/31/1995")
                                .param("street_number", "2")
                                .param("street_name", "rue de la Paix")
                                .param("zip_code", "75002")
                                .param("city", "Paris")
                                .param("country", "FRANCE")
                                .param("username", "paulb")
                                .param("password", "P@55w0rd"))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andReturn().getResolvedException().getMessage();
        Assert.assertEquals(error, expected);
    }

    @Test
    public void testCreateContractorWithInvalidLastname() throws Exception {
        String expected = "Barri& is not a valid value for the field Lastname";
        String error = this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/contractor")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("first_name", "Paul")
                                .param("last_name", "Barri&")
                                .param("email", "paul@gmail.com")
                                .param("birth", "10/31/1995")
                                .param("street_number", "2")
                                .param("street_name", "rue de la Paix")
                                .param("zip_code", "75002")
                                .param("city", "Paris")
                                .param("country", "FRANCE")
                                .param("username", "paulb")
                                .param("password", "P@55w0rd"))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andReturn().getResolvedException().getMessage();
        Assert.assertEquals(error, expected);
    }


    @Test
    public void testGetOneContractor() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/contractors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void testGetAllContractors() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/contractors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void testAddCreditCardPayment() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/contractor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("first_name", "Paul")
                        .param("last_name", "Barrié")
                        .param("email", "paul@gmail.com")
                        .param("birth", "31/10/1995")
                        .param("street_number", "2")
                        .param("street_name", "rue de la Paix")
                        .param("zip_code", "75002")
                        .param("city", "Paris")
                        .param("country", "FRANCE")
                        .param("username", "paulb")
                        .param("password", "P@55w0rd"))
                .andDo(
                        result -> {
                            mockMvc.perform(MockMvcRequestBuilders.post("/payment")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("contractor_id", "1")
                                    .param("owner", "Paul Barrié")
                                    .param("number", "1234567890123456")
                                    .param("expiration", "12/20")
                                    .param("security_number", "123"))
                                    .andExpect(MockMvcResultMatchers.status().is(200))
                                    .andExpect(MockMvcResultMatchers.jsonPath("$.payment.id.value").value(2))
                                    .andExpect(MockMvcResultMatchers.jsonPath("$.payment.contractorID.value").value(1))
                                    .andExpect(MockMvcResultMatchers.jsonPath("$.payment.paymentMode.ownerName").value("Paul Barrié"))
                                    .andExpect(MockMvcResultMatchers.jsonPath("$.payment.paymentMode.cardNumber").value("1234567890123456"))
                                    .andExpect(MockMvcResultMatchers.jsonPath("$.payment.paymentMode.expirationDate").value("12/20"));
                        }
                );
    }

    @Test
    public void testAddAccountIdentityPayment() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/contractor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("first_name", "Paul")
                        .param("last_name", "Barrié")
                        .param("email", "paul@gmail.com")
                        .param("birth", "31/10/1995")
                        .param("street_number", "2")
                        .param("street_name", "rue de la Paix")
                        .param("zip_code", "75002")
                        .param("city", "Paris")
                        .param("country", "FRANCE")
                        .param("username", "paulb")
                        .param("password", "P@55w0rd"))
                .andDo(
                        result -> {
                            mockMvc.perform(MockMvcRequestBuilders.post("/payment")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .param("contractor_id", "1")
                                            .param("account_identifier", "FR456546485475674545644")
                                            .param("bank_identifier", "1234567890123456"))
                                    .andExpect(MockMvcResultMatchers.status().is(200))
                                    .andExpect(MockMvcResultMatchers.jsonPath("$.payment.id.value").value(1))
                                    .andExpect(MockMvcResultMatchers.jsonPath("$.payment.contractorID.value").value(1))
                                    .andExpect(MockMvcResultMatchers.jsonPath("$.payment.paymentMode.accountIdentifier").value("FR456546485475674545644"))
                                    .andExpect(MockMvcResultMatchers.jsonPath("$.payment.paymentMode.bankIdentifier").value("1234567890123456"));
                        }
                );
    }
}