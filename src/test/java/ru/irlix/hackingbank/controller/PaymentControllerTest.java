package ru.irlix.hackingbank.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.hackingbank.repository.PaymentRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentRepository paymentRepository;


    @Test
     void testGetAllPayments() throws Exception {

        // Выполнение GET запроса к /payments
        mockMvc.perform(get("/payments")
                /*Указываем, что тип содержимого запроса будет text/html*/
                        .contentType(MediaType.TEXT_HTML))
                /*проверяем, что статус ответа 200*/
                .andExpect(status().isOk())
                /*проверяем, что контроллер вернул представление с именем*/
                .andExpect(view().name("payments"))
                /*проверяем, что в модели будут атрибуты*/
                .andExpect(model().attributeExists("payments"))
                .andExpect(model().attributeExists("clientNames"))
                /*выводит в консоль запрос и ответ*/
                .andDo(print());
    }

    @Test
    void testGetShowNewForm() throws Exception {

        mockMvc.perform(get("/payments/new")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentAdd"))
                .andExpect(model().attributeExists("clients"))
                .andExpect(model().attributeExists("payment"))
                .andDo(print());
    }
}