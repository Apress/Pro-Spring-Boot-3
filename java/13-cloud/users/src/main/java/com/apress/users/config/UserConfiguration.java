package com.apress.users.config;

// With Consul (Part 1)

import com.apress.users.model.User;
import com.apress.users.model.UserRole;
import com.apress.users.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties({UserProperties.class})
public class UserConfiguration {

    @Bean
    CommandLineRunner init(UserService userService){
        return args -> {
            userService.saveUpdateUser(new User("ximena@email.com","Ximena","https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar","aw2s0meR!", List.of(UserRole.USER),true));
            userService.saveUpdateUser(new User("norma@email.com","Norma" ,"https://www.gravatar.com/avatar/f07f7e553264c9710105edebe6c465e7?d=wavatar", "aw2s0meR!", List.of(UserRole.USER, UserRole.ADMIN),false));
        };
    }
}






// With Vault and Consul (Part 2)
/*

import com.apress.users.model.User;
import com.apress.users.model.UserRole;
import com.apress.users.service.UserService;
import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.core.lease.SecretLeaseContainer;
import org.springframework.vault.core.lease.domain.RequestedSecret;
import org.springframework.vault.core.lease.event.SecretLeaseCreatedEvent;
import org.springframework.vault.core.lease.event.SecretLeaseExpiredEvent;


@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties({UserProperties.class})
public class UserConfiguration {

    @Bean
    CommandLineRunner init(UserService userService){
        return args -> {
            userService.saveUpdateUser(new User("ximena@email.com","Ximena","https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar","aw2s0meR!", Arrays.asList(UserRole.USER),true));
            userService.saveUpdateUser(new User("norma@email.com","Norma" ,"https://www.gravatar.com/avatar/f07f7e553264c9710105edebe6c465e7?d=wavatar", "aw2s0meR!", Arrays.asList(UserRole.USER, UserRole.ADMIN),false));

        };
    }

    @Value("${spring.cloud.vault.database.role}")
    private String databaseRoleName;
    private final SecretLeaseContainer secretLeaseContainer;
    private final HikariDataSource hikariDataSource;

    @PostConstruct
    private void postConstruct() {
        final String vaultCredentialsPath = String.format("database/creds/%s", databaseRoleName);

        secretLeaseContainer.addLeaseListener(event -> {
            log.info("[SecretLeaseContainer]> Received event: {}", event);

            if (vaultCredentialsPath.equals(event.getSource().getPath())) {
                if (event instanceof SecretLeaseExpiredEvent &&
                        event.getSource().getMode() == RequestedSecret.Mode.RENEW) {
                    log.info("[SecretLeaseContainer]> Let's replace the RENEWED lease by a ROTATE one.");
                    secretLeaseContainer.requestRotatingSecret(vaultCredentialsPath);
                } else if (event instanceof SecretLeaseCreatedEvent secretLeaseCreatedEvent &&
                        event.getSource().getMode() == RequestedSecret.Mode.ROTATE) {
                    String username = secretLeaseCreatedEvent.getSecrets().get("username").toString();
                    String password = secretLeaseCreatedEvent.getSecrets().get("password").toString();

                    updateHikariDataSource(username, password);
                }
            }
        });
    }

    private void updateHikariDataSource(String username, String password) {

        log.info("[SecretLeaseContainer]> Soft evict the current database connections");
        HikariPoolMXBean hikariPoolMXBean = hikariDataSource.getHikariPoolMXBean();
        if (hikariPoolMXBean != null) {
            hikariPoolMXBean.softEvictConnections();
        }

        log.info("[SecretLeaseContainer]> Update database credentials with the new ones.");
        HikariConfigMXBean hikariConfigMXBean = hikariDataSource.getHikariConfigMXBean();
        hikariConfigMXBean.setUsername(username);
        hikariConfigMXBean.setPassword(password);
    }
}
*/


