package com.apress.myretro.board;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Card {


    @Id
    private UUID id;

    @NotBlank
    private String comment;

    @NotNull
    private CardType cardType;

    @ManyToOne
    @JoinColumn(name = "retro_board_id")
    @JsonIgnore
    RetroBoard retro_board;

    public Card(String comment, CardType cardType) {
        this.comment = comment;
        this.cardType = cardType;
    }

    @PrePersist
    private void prePersist(){
        if (this.id == null)
            this.id = UUID.randomUUID();
    }
}
