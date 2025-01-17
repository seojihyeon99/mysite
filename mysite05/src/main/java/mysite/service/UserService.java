package mysite.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mysite.repository.UserRepository;
import mysite.vo.UserVo;

@Service
public class UserService {
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	public void join(UserVo userVo) {
		userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
		userRepository.insert(userVo);
	}

	public UserVo getUser(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	public UserVo getUser(Long id) {
		return userRepository.findById(id);
	}

	public UserVo getUser(String email) {
		UserVo userVo = userRepository.findByEmail(email, UserVo.class);
		userVo.setPassword("");
		return userVo;
	}

	public void update(UserVo userVo) {
		userRepository.update(userVo);
	}
}
