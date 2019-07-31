package by.haidash.microservice.auth.repository;

import by.haidash.microservice.auth.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InternalUserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
