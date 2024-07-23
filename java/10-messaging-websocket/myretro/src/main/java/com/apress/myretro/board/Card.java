package com.apress.myretro.board;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Card {


    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    @Id
    @NotNull
    private UUID cardId;

    @NotBlank
    private String comment;

    @NotNull
    private CardType cardType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable=true,updatable=false)
    @NotNull
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable=true,updatable=true)
    @NotNull
    private LocalDateTime modified;

    @PrePersist
    public void onPrePersist() {
        this.created = LocalDateTime.now();
        this.modified = LocalDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.modified = LocalDateTime.now();
    }

}
