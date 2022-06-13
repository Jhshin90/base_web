package com.work.baseWeb.repository;

import com.work.baseWeb.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

// JpaRepository 를 상속하면 자동 컴포넌트 스캔됨.
public interface LoginRepository extends JpaRepository<User, Integer>{

	User findByUsername(String username);

	static final String UPDATE_USER_LAST_LOGIN = "UPDATE USER "
			+ "SET LAST_LOGIN_TIME = :lastLoginTime "
			+ "WHERE USERNAME = :username";

	@Transactional
	@Modifying
	@Query(value=UPDATE_USER_LAST_LOGIN, nativeQuery = true)
	public int updateUserLastLogin(@Param("username") String username, @Param("lastLoginTime") LocalDateTime lastLoginTime);


}
