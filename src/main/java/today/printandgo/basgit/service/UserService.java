package today.printandgo.basgit.service;

import today.printandgo.basgit.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
