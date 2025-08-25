package com.NovaMind.Project.NovaMind.Services;

import com.NovaMind.Project.NovaMind.Documents.Badge;
import com.NovaMind.Project.NovaMind.Documents.User;
import com.NovaMind.Project.NovaMind.Repositories.BadgesRepository;
import com.NovaMind.Project.NovaMind.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    private final BadgesRepository badgeRepository;

    public UserService(UserRepository userRepository, BadgesRepository badgeRepository) {
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Retrieve a user by their ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Create a new user with initial values
    public User createUser(String username, String firstName, String lastName, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPassword(password);
        user.setXP(0L);
        user.setCreatedAt(LocalDate.now());
        user.setLastActiveDate(LocalDate.now());

        return userRepository.save(user);
    }

    // Update the XP value for a user
    public User updateUserXP(Long id, Long xp) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setXP(xp);
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found with id: " + id);
    }

    // Update the streak count for a user
    public User updateUserStreak(Long id, int streakCount) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStreakCount(streakCount);
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found with id: " + id);
    }

    // Update the current level of the user by setting currentLevelId
    public User updateUserCurrentLevel(Long id, int currentLevelId) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCurrentLevelId(currentLevelId);
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found with id: " + id);
    }
    public User addBadgeToUser(Long userId, String badgeId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        Optional<Badge> badgeOptional = badgeRepository.findById(badgeId);
        if (badgeOptional.isEmpty()) {
            throw new RuntimeException("Badge not found with id: " + badgeId);
        }
        User user = userOptional.get();
        Badge badge = badgeOptional.get();
        // Optionally, check to avoid duplicates
        if (!user.getEarnedBadges().contains(badge)) {
            user.getEarnedBadges().add(badge);
        }
        return userRepository.save(user);
    }
}
