package com.template.webtemplate.service;

import com.template.webtemplate.entity.Member;
import com.template.webtemplate.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MemberDetailService implements UserDetailsService {

    @Autowired
    private MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = repository.findByEmail(username);
        if(member == null)
        {
            throw new UsernameNotFoundException("멤버를 찾지 못했습니다.");
        }
        return new MemberDetail(member);
    }
}
