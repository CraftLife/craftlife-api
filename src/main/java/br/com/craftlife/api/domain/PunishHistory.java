package br.com.craftlife.api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "litebans_history")
public class PunishHistory {

    @Id
    @GeneratedValue
    private Integer id;

    private Date date;

    private String name;

    private String uuid;

    private String ip;

}
