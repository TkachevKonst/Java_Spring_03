//package ru.gb.security;
//
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//
//import java.util.List;
//@Component
//
//public class MyCustomUserDetailsService implements UserDetailsService {
//
//
//    private final UserRepository userRepository;
//
//
//    public MyCustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//
//
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByLogin(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
//        List<SimpleGrantedAuthority> userRoles = user.getRoles().stream()
//                .map(it -> new SimpleGrantedAuthority(it.getName())).toList();
//        return new org.springframework.security.core.userdetails.User(
//                user.getLogin(),
//                user.getPassword(),
//                userRoles);
//    }
//}
