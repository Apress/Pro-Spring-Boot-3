package com.apress.users.actuator;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogEventConfig {

    private Boolean enabled = false;

    private String prefix = ">> ";

    private String postfix = " <<";
}
