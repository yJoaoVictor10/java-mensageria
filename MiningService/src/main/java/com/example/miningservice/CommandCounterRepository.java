package com.example.miningservice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommandCounterRepository extends JpaRepository<CommandCounter, Long> {
    Optional<CommandCounter> findByCommand(String command);
}
