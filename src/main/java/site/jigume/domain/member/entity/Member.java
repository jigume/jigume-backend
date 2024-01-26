package site.jigume.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import site.jigume.domain.order.entity.Sell;
import site.jigume.global.audit.BaseTimeEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false)
    private Long id;

    @Column(unique = true)
    private String socialId;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String refreshToken;

    private String profileImageUrl;

    private Point coordinate;

    @Enumerated(EnumType.STRING)
    private BaseRole baseRole;

    @OneToMany(mappedBy = "member")
    private List<Sell> sellList = new ArrayList<>();

    public static Member createMember(String socialId) {
        Member member = new Member();
        member.socialId = socialId;
        member.baseRole = BaseRole.GUEST;

        return member;
    }

    public void updateMemberInfo(String nickname, Point coordinate) {
        this.nickname = nickname;
        this.coordinate = coordinate;
    }

    public void updateBaseRole(BaseRole baseRole) {
        this.baseRole = baseRole;
    }

    public void updateMemberProfileImg(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.baseRole.getKey()));
    }

    @Override
    public String getPassword() {
        return socialId;
    }

    @Override
    public String getUsername() {
        return socialId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
