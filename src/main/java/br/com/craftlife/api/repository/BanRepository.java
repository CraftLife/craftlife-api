package br.com.craftlife.api.repository;

import br.com.craftlife.api.domain.PunishBan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanRepository extends JpaRepository<PunishBan, Integer> {

    Page<PunishBan> findAllByOrderByIdDesc(PageRequest pageRequest);
}
