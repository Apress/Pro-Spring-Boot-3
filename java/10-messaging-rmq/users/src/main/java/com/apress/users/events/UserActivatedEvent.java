package com.apress.users.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserActivatedEvent {


    private final String action = "ACTIVATION_STATUS";

    private String email;

    private boolean active;
}
