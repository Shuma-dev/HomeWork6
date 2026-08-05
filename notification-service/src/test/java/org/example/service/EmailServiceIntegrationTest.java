package org.example.service;

import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.example.event.Operation;
import org.example.event.UserEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import jakarta.mail.internet.MimeMessage;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class EmailServiceIntegrationTest {

    @Autowired
    private EmailService service;

    @BeforeEach
    void setUp() {
        greenMail.reset();
    }

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP);

    @Test
    @DisplayName("Должен отправить письмо при создании пользователя")
    public void shouldSendCreateEmail() throws Exception {
        UserEvent event = new UserEvent(Operation.CREATE, "test@example.com");
        service.sendEmail(event);
        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("test@example.com", messages[0].getAllRecipients()[0].toString());
        assertEquals("Добро пожаловать!", messages[0].getSubject());
        String body = (String) messages[0].getContent();
        assertTrue(body.contains("Ваш аккаунт на сайте Microservices Project был успешно создан."));
    }

    @Test
    @DisplayName("Должен отправить письмо при удалении пользователя")
    public void shouldSendDeleteEmail() throws Exception {
        UserEvent event = new UserEvent(Operation.DELETE, "test@example.com");
        service.sendEmail(event);
        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("test@example.com", messages[0].getAllRecipients()[0].toString());
        assertEquals("Аккаунт удален", messages[0].getSubject());
        String body = (String) messages[0].getContent();
        assertTrue(body.contains("Ваш аккаунт был удален."));
    }
}
