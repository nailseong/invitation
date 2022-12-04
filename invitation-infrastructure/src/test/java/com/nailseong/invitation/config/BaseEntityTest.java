package com.nailseong.invitation.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.nailseong.invitation.TestConfiguration;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({
        TestConfiguration.class,
        JpaConfig.class
})
class BaseEntityTest {

    @Autowired
    private MockEntityRepository repository;

    @Test
    @DisplayName("BaseEntity를 상송한 엔티티에 Auditing이 적용된다.")
    void auditing() {
        // given
        final MockEntity entity = new MockEntity();

        // when
        final MockEntity actual = repository.save(entity);

        // then
        assertThat(actual).extracting(MockEntity::getId).isEqualTo(1L);
        assertThat(actual).extracting(MockEntity::getCreatedAt).isNotNull();
        assertThat(actual).extracting(MockEntity::getUpdatedAt).isNotNull();
    }

    @Entity
    class MockEntity extends BaseEntity {
    }
}
