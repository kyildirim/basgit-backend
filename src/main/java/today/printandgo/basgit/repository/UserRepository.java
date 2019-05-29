package today.printandgo.basgit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import today.printandgo.basgit.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
    User findByCurrentpassport(String currentpassport);
}
