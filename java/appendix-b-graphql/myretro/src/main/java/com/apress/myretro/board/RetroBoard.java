package com.apress.myretro.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

import java.util.List;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "retro_board")
public class RetroBoard {

    @Id
    private UUID id;

    @NotBlank(message = "A name must be provided")
    private String name;

    @Singular
    @OneToMany(mappedBy = "retro_board")
    private List<Card> cards;

    @PrePersist
    private void prePersist(){
        if (this.id == null)
            this.id = UUID.randomUUID();
    }

}
