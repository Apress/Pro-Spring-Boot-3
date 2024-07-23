package com.apress.users;

import org.springframework.data.relational.core.conversion.MutableAggregateChange;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserBeforeSaveCallback implements BeforeSaveCallback<User> {
    @Override
    public User onBeforeSave(User aggregate, MutableAggregateChange<User> aggregateChange) {
        if (aggregate.getGravatarUrl()==null)
            aggregate.setGravatarUrl(UserGravatar.getGravatarUrlFromEmail(aggregate.getEmail()));
        if (aggregate.getUserRole() == null)
            aggregate.setUserRole(List.of(UserRole.INFO));
        return aggregate;
    }
}
