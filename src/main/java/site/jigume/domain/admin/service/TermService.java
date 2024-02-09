package site.jigume.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.admin.dto.TermDto;
import site.jigume.domain.admin.entity.Term;
import site.jigume.domain.admin.repository.TermRepository;
import site.jigume.domain.member.entity.BaseRole;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.member.exception.auth.AuthExceptionCode;
import site.jigume.domain.member.service.MemberService;

import static site.jigume.domain.member.exception.auth.AuthExceptionCode.*;

@Service
@RequiredArgsConstructor
public class TermService {

    private final TermRepository termRepository;
    private final MemberService memberService;

    @Transactional
    public Long save(TermDto termDto) {
        Member member = memberService.getMember();

        isAdmin(member);

        Term term = termDto.toTerm();

        return termRepository.save(term).getId();
    }

    private void isAdmin(Member member) {
        if(member.getBaseRole() != BaseRole.ADMIN) {
            throw new AuthException(NOT_AUTHORIZATION_USER);
        }
    }
}
