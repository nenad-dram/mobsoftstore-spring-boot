package com.endyary.mobsoftstore.rating;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.endyary.mobsoftstore.application.Application;
import com.endyary.mobsoftstore.user.User;

/**
 * Application rating repository definition
 */
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> getByApplicationAndUser(Application application, User user);
}
