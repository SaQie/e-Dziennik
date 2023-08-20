package pl.edziennik.infrastructure.strategy.confirmation.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import pl.edziennik.common.valueobject.vo.Token;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.token.ActivationTokenRepository;
import pl.edziennik.infrastructure.repository.user.UserCommandRepository;
import pl.edziennik.infrastructure.strategy.confirmation.SendConfirmationMessageStrategy;
import pl.edziennik.infrastructure.strategy.confirmation.SendConfirmationMessageStrategyData;
import pl.edziennik.infrastructure.strategy.confirmation.email.EmailConfirmationMessageViewStrategy;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class SendConfirmationMessageByEmailStrategy implements SendConfirmationMessageStrategy {

    private final UserCommandRepository userCommandRepository;
    private final ActivationTokenRepository tokenRepository;
    private final JavaMailSender sender;
    private final List<EmailConfirmationMessageViewStrategy> emailConfirmationMessageViewStrategy;

    @Override
    public void sendMessage(SendConfirmationMessageStrategyData data) {
        User user = userCommandRepository.getUserByUserId(data.userId());
        UUID activationToken = UUID.randomUUID();

        String mailViewByStrategy = emailConfirmationMessageViewStrategy.stream()
                .filter(EmailConfirmationMessageViewStrategy::isEnabled)
                .map(strategy -> strategy.getHTMLMessage(Token.of(activationToken), user.userId()))
                .findFirst()
                .orElse(StringUtils.EMPTY);
        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom("E-Diary");
            message.setSubject("Confirmation token");
            message.setText(mailViewByStrategy);
            message.setTo(user.email().value());

            // TODO uncomment if needed
//            sender.send(mimeMessage);

            tokenRepository.insertActivationToken(user.userId(), activationToken);
        } catch (MessagingException e) {
            log.error("Error during sending an email message to email " + user.email().value());
            log.error(e.getMessage());
        }
        log.debug("User " + user.username()+ " Email: " + user.email()+ " Activation token: " + activationToken);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
