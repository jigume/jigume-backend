package site.jigume.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.admin.entity.Term;
import site.jigume.domain.admin.exception.TermException;
import site.jigume.domain.admin.repository.TermRepository;
import site.jigume.domain.member.dto.term.TermYnDto;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.entity.TermYn;
import site.jigume.domain.member.repository.TermYnRepository;

import java.util.List;

import static site.jigume.domain.admin.exception.TermExceptionCode.*;

@Service
@RequiredArgsConstructor
public class TermYnService {

    private final TermYnRepository termYnRepository;
    private final TermRepository termRepository;
    private final MemberService memberService;

    @Transactional
    public void save(List<TermYnDto> termYnDtoList) {
        Member member = memberService.getMember();

        List<Term> termList = termRepository.findAll();

        if (termYnDtoList.stream()
                .distinct().count() != termYnDtoList.size()) {
            throw new TermException(TERM_DUPLICATED_ERROR);
        }

        termYnDtoList.stream().forEach(termYnDto -> {
            Term term = termList.stream()
                    .filter(t -> t.getId().equals(termYnDto.termId()))
                    .findAny()
                    .orElseThrow(() -> new TermException(TERM_NOT_FOUND));

            if (term.isRequiredYn() && !termYnDto.termYn()) {
                throw new TermException(TERM_REQUIRE_ERROR);
            }

            TermYn termYn = termYnDto.toTermYn(term, member);

            termYnRepository.save(termYn);
        });
    }
}
