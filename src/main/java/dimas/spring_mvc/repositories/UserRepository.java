package dimas.spring_mvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dimas.spring_mvc.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmailAndPassword(String email, String password);
}
