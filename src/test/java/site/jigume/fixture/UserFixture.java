package site.jigume.fixture;

import site.jigume.domain.member.entity.BaseRole;
import site.jigume.domain.member.entity.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserFixture {

    public static Member createMember() {
        Member member = Member.createMember("id");

        member.updateMemberInfo("id", 1.0, 2.0);
        member.updateMemberProfileImg("url");
        member.updateBaseRole(BaseRole.USER);

        return member;
    }

    public static Member createGuestMember() {
        Member member = Member.createMember("guest");

        member.updateMemberInfo("guest", 1.0, 2.0);
        member.updateMemberProfileImg("url");

        return member;
    }

    public static Member createCustomMember(String str) {
        Member member = Member.createMember(str);

        member.updateMemberInfo(str, 1.0, 2.0);
        member.updateMemberProfileImg("url");

        return member;
    }
    public static void setUpCustomAuth(Member member) {
        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(member.getSocialId())
                .password(member.getSocialId())
                .roles(member.getBaseRole().convertBaseRole())
                .build();

        GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsUser, null
                , authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
