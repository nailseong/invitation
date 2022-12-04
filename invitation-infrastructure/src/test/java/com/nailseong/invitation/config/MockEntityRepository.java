package com.nailseong.invitation.config;

import com.nailseong.invitation.config.BaseEntityTest.MockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MockEntityRepository extends JpaRepository<MockEntity, Long> {
}
