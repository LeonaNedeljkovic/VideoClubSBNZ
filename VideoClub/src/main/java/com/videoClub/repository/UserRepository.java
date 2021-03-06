package com.videoClub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.videoClub.model.RegisteredUser;
import com.videoClub.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	//User findOneByUsername(String username);

	@Query(value = "select * from users u where u.id =(select t.user from verification_tokens t where t.token = ?1)", nativeQuery = true)
	User findByToken(String token);

	public User findByUsername(String username);
	
	public User findById(long id);
	
	@Query(value = "select * from user u where dtype = 'RegisteredUser'", nativeQuery = true)
	public List<RegisteredUser> findAllRegisteredUsers();
}
