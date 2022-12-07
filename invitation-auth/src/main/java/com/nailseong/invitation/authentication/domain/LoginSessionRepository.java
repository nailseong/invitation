package com.nailseong.invitation.authentication.domain;

import org.springframework.data.repository.CrudRepository;

public interface LoginSessionRepository extends CrudRepository<LoginSession, String> {
}
