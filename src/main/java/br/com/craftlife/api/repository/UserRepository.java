package br.com.craftlife.api.repository;

import br.com.craftlife.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsernameIgnoreCase(String username);

    @Query("SELECT u FROM User u WHERE u.ip = :ip AND u.lastLogin >= :minLastLogin")
    List<User> findByIpAndLastLoginAfter(@Param("ip") String ip, @Param("minLastLogin") Long minLastLogin);

}
