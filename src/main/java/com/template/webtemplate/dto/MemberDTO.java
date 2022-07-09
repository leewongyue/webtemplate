package com.template.webtemplate.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String email;
    private String userpw;
    private String nickname;

    private String name;
    public MemberDTO(String email, String userpw, String nickname, String name) {
        this.email = email;
        this.userpw = userpw;
        this.nickname = nickname;
        this.name = name;
    }


    private LocalDateTime regDate,modDate;
}
