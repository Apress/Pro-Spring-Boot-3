package com.apress.users;

import java.util.Optional;

public interface Repository<D,ID>{

    D save(D domain);

    Optional<D> findById(ID id);

    Iterable<D> findAll();

    void deleteById(ID id);
}
