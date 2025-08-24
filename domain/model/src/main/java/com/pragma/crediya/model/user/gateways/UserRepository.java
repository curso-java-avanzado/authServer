package com.pragma.crediya.model.user.gateways;

public interface UserRepository {

    // Define your repository methods here
    void save(User user);

    Optional<User> findById(Long id);

    List<User> findAll();

    void update(User user);

    void delete(Long id);
}
