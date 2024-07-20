package br.com.craftlife.api.controller;

import br.com.craftlife.api.controller.dto.PageableResponse;
import br.com.craftlife.api.domain.PunishBan;
import br.com.craftlife.api.repository.BanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


@RequestMapping("punishment")
@RestController
@RequiredArgsConstructor
public class PunishmentController {

    private final BanRepository banRepository;

    @GetMapping("bans")
    @Secured({"STAFF", "ADMIN"})
    public PageableResponse<PunishBan> getBans(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (Objects.isNull(page) || page < 1)
            page = 1;
        if (Objects.isNull(size) || size < 1)
            size = 10;
        if (size > 100)
            size = 100;

        Page<PunishBan> pageableData= banRepository.findAllByOrderByIdDesc(PageRequest.of(page - 1, size));

        return PageableResponse.<PunishBan>builder()
                .content(pageableData.getContent())
                .page(pageableData.getNumber() + 1)
                .size(pageableData.getSize())
                .totalElements(pageableData.getTotalElements())
                .totalPages(pageableData.getTotalPages())
                .build();
    }
}
