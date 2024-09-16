package com.endyary.mobsoftstore.application;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Application repository definition
 */
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByCategory(Category category);

    boolean existsByName(String name);

    List<Application> findTop5ByOrderByDownloadCountDesc();
}
