package dimas.spring_mvc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dimas.spring_mvc.entities.User;
import dimas.spring_mvc.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User validateUser(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}
	
}
