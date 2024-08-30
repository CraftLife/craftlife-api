package br.com.craftlife.api.service;

import br.com.craftlife.api.controller.dto.GoalResponse;
import br.com.craftlife.api.controller.dto.ServerStatusResponse;
import br.com.craftlife.api.domain.Payment;
import br.com.craftlife.api.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ServerService {

    private final RestTemplate restTemplate = new RestTemplate();

    private ServerStatusResponse lastServerStatusResponse = new ServerStatusResponse();

    private final PaymentRepository paymentRepository;

    public ServerStatusResponse getStatus() {
        this.lastServerStatusResponse = ServerStatusResponse.builder()
                .server(this.getServerStatus())
                .discord(this.getDiscordStatus())
                .build();

        return lastServerStatusResponse;
    }

    public ServerStatusResponse.Server getServerStatus() {
        ServerStatusResponse.Server responseServer = null;
        try {
            responseServer =
                    restTemplate.getForObject("https://api.mcsrvstat.us/2/jogar.craftlife.com.br", ServerStatusResponse.Server.class);
        } catch (HttpClientErrorException ignored) {
        }
        return Objects.nonNull(responseServer) ? responseServer : lastServerStatusResponse.getServer();
    }

    public ServerStatusResponse.Discord getDiscordStatus() {
        ServerStatusResponse.Discord responseDiscord = null;
        try {
            responseDiscord =
                    restTemplate.getForObject("https://discordapp.com/api/guilds/94235856516153344/widget.json", ServerStatusResponse.Discord.class);
        } catch (HttpClientErrorException ignored) {
        }
        return Objects.nonNull(responseDiscord) ? responseDiscord : lastServerStatusResponse.getDiscord();
    }

    public GoalResponse getRsGoal() {

        List<Payment> payments = paymentRepository.findAllByDateApprovedBetweenOrderByDateApprovedDesc(
                LocalDateTime.of(2024, 5, 10, 0, 0, 0),
                LocalDateTime.of(2024, 5, 30, 23, 59, 59)
        );
        GoalResponse rsGoalResponse = GoalResponse.builder()
            .goal(300D)
            .collected(payments.stream().mapToDouble(Payment::getTransactionAmount).sum())
            .lastDonations(
                payments.subList(0, Math.min(payments.size(), 3)).stream()
                    .map(payment ->
                        GoalResponse.RsDonation.builder()
                            .username(payment.getUser().getUsername())
                            .productName(payment.getProduct().getName())
                            .transactionAmount(payment.getTransactionAmount())
                        .build()
                    )
                    .toList())
            .build();
        ;
        return rsGoalResponse;
    }
}
