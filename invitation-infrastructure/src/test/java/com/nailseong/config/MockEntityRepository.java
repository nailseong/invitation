package com.nailseong.config;

import com.nailseong.config.BaseEntityTest.MockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MockEntityRepository extends JpaRepository<MockEntity, Long> {
}
