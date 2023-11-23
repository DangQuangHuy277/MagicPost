package com.magicpost.app.magicPost.user;

import com.magicpost.app.magicPost.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
