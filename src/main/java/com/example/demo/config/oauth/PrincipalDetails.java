//package com.example.demo.config.oauth;
//
//
//import java.util.ArrayDeque;
//import java.util.Collection;
//import com.example.demo.entity.UserEntity;
//import org.springframework.security.core.GrantedAuthority;
//
//import org.springframework.security.core.userdetails.UserDetails;
//
////시큐리티가 로그인 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
////로그인 진행이 완료가 되면 시큐리티 세션을 만들어 준다(Security ContextHolder라는 키값에 세션 저장)
////세션에 들어갈 수 있는 정보는 즉 시큐리티가 가지고 있는 세션에 들어갈 수 있는 오브젝트는 정해져있다.
////Object 타입 => Authentication 타입 객체
////Authentication 안에 User 정보가 있다.
////User Object 타입 => UserDetails 타입 객체
//
////Security Session => Authentication => UserDetails
////     시큐리티 세션   => 들어갈 수 있는 객체 => 객체 안의 정보
//
//
//public class PrincipalDetails implements UserDetails {
//
//    private UserEntity user;
//
//    public PrincipalDetails(UserEntity user) {
//        this.user = user;
//    }
//
//    public UserEntity getUser() {
//        return user;
//    }
//
////    해당 유저의 권한 리턴
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collect = new ArrayDeque<>();
//        collect.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return user.getRoles();
//            }
//        });
//
//        return collect;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
//
//
