package com.apress.myretro.model;

import org.springframework.data.repository.CrudRepository;

public interface MyRetroAuditEventRepository extends CrudRepository<MyRetroAuditEvent, Long> {
}
