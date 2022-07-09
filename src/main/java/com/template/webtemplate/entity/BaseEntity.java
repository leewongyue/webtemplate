package com.template.webtemplate.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//https://bum752.github.io/posts/JPA-entity-listener-만들기/ ->비밀번호 저장 데이터베이스 설계 관련
//https://kangwoojin.github.io/programing/jpa-entity-listeners/ 이벤트 리스너
//MappedSuperclass -> 상속받는 클래스에 매핑정보 제공(컬럼으로 인식)
@MappedSuperclass
//auditing 기능을 포함 (트랜잭션 커밋 시점에 플러시 호출될때 하이버네이트가 자동으로 시간 값을 채워준다.)
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity {
    //처음 시작 시간
    @CreatedDate
    @Column(name = "regdata",updatable = false)
    private LocalDateTime regDate;
    //최근 변경시간
    @LastModifiedDate
    @Column(name="moddate")
    private LocalDateTime modDate;
}
