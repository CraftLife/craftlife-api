package br.com.craftlife.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
@Table(name = "litebans_bans")
public class PunishBan {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uuid", referencedColumnName = "uuid")
    @JsonProperty("banned_data")
    private PunishHistory history;

    private String ip;

    private String reason;

    private String banned_by_uuid;

    private String banned_by_name;

    private String removed_by_uuid;

    private String removed_by_name;

    private Date removed_by_date;

    private Long time;

    private Long until;

    private String server_scope;

    private String server_origin;

    private Boolean silent;

    private Boolean ipban;

    private Boolean ipban_wildcard;

    private Boolean active;
}
