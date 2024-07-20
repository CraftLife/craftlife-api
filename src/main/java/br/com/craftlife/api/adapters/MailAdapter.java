package br.com.craftlife.api.adapters;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class MailAdapter {

    private final Logger logger = LoggerFactory.getLogger(MailAdapter.class);

    private final JavaMailSender javaMailSender;
    private final MjmlServerAdapter mjmlServerAdapter;
    private final TemplateEngine templateEngine;

    public void sendHtmlEmail(String[] to, String subject, Context context, String templateContent) {

        logger.info(String.format("Enviando email subject (%s) para %s", subject, Arrays.toString(to)));
        String mjmlTemplate = buildTemplate(context, templateContent);
        String mailBody = mjmlServerAdapter.renderMjmlToHtml(mjmlTemplate).getHtml();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setFrom("CraftLife <no-reply@craftlife.com.br>");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(mailBody != null ? mailBody : "", true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Tratar a exceção conforme necessário
        }
    }

    private String buildTemplate(Context context, String templateContent) {
        StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();
        stringTemplateResolver.setTemplateMode(TemplateMode.XML);
        templateEngine.setTemplateResolver(stringTemplateResolver);
        return templateEngine.process(templateContent, context);
    }
}
