package com.apress.myretro.board;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class RetroBoard {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    @NotBlank(message = "A name must be provided")
    private String name;

    @Singular
    @OneToMany(mappedBy = "retroBoard")
    private List<Card> cards = new ArrayList<>();
}
