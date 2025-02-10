package dimas.spring_mvc.services;

import dimas.spring_mvc.entities.User;

public interface UserService {

	User save(User user);
	User validateUser(String email, String password);
	
}
