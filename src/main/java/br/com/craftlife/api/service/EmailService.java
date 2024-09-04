package br.com.craftlife.api.service;

import br.com.craftlife.api.adapters.MailAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final MailAdapter mailAdapter;

    private final NumberFormat formatCurrency = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @Value("classpath:templates/product-puchased.mjml")
    Resource productPuchasedTemplate;

    public void sendPurchaseEmail(String recipient, String fullname, String username, String productName, LocalDateTime approvedDate, Double price) throws IOException {
        Context context = new Context();
        context.setVariable("fullname", fullname);
        context.setVariable("ign", username);
        context.setVariable("productName", productName);
        context.setVariable("date", approvedDate);
        context.setVariable("price", formatCurrency.format(price));

        mailAdapter.sendHtmlEmail(new String[]{recipient}, "CraftLife Store - Agradecemos por sua compra!", context, getMjmlContent(productPuchasedTemplate));
    }

    public String getMjmlContent(Resource resource) throws IOException {
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }
}
