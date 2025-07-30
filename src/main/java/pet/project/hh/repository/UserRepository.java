package pet.project.hh.repository;

import pet.project.hh.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.type.id = 2")
    List<User> findAllEmployers();

    @Query("select u from User u where u.type.id = 1")
    List<User> findAllApplicants();

    @Query("select u from User u where u.name like '%:name%'")
    List<User> findAllByNameExample(String name);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);

    Optional<User> findByResetPasswordToken(String token);
}
