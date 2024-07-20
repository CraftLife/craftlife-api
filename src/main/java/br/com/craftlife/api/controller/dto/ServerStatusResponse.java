package br.com.craftlife.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServerStatusResponse {

    private Server server;

    private Discord discord;

    @Data
    public static class Server {
        private Players players;
    }

    @Data
    public static class Players {
        private String online;
        private String max;
    }

    @Data
    public static class Discord {

        @JsonProperty("presence_count")
        private String presenceCount;
    }
}
