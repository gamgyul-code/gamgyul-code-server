package com.gamgyul_code.halmang_vision.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Member findByUsername(String username);

    Optional<Member> findByIdAndUsername(Long id, String username);
}
