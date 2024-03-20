package br.com.goldinvesting.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.goldinvesting.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Modifying
    @Query(value="UPDATE user set user.name = ?2, user.email = ?3, user.password = ?4 WHERE user.id = ?1", nativeQuery=true)
    void setUser(Long id, String name, String email, String password);
}
