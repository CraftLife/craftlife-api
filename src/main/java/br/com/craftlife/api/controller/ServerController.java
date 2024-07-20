package br.com.craftlife.api.controller;

import br.com.craftlife.api.controller.dto.GoalResponse;
import br.com.craftlife.api.controller.dto.ServerStatusResponse;
import br.com.craftlife.api.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "server")
@RequiredArgsConstructor
public class ServerController {

    private final ServerService serverService;

    @GetMapping("status")
    public ServerStatusResponse getStatus() {
        return serverService.getStatus();
    }

    @GetMapping("goal")
    public GoalResponse getRsGoal() {
        return serverService.getRsGoal();
    }
}
