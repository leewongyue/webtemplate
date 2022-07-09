package com.template.webtemplate.repository;

import com.template.webtemplate.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member,Long> {

    @Query("SELECT u FROM Member u where u.email = ?1")
    Member findByEmail(String email);
}
