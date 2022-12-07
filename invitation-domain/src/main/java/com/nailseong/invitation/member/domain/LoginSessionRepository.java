package com.nailseong.invitation.member.domain;

import org.springframework.data.repository.CrudRepository;

public interface LoginSessionRepository extends CrudRepository<LoginSession, String> {
}
