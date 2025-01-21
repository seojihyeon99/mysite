package mysite.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import mysite.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// UserDetails는 인터페이스임 => But, UserVo에서 impl 받아 직접 구현하기엔 부담스러움 => UserDetailsImpl를 만들어 UserVo를 상속받고, UserDetails를 impl하여 해결
		return userRepository.findByEmail(username, UserDetailsImpl.class);
	}

}
