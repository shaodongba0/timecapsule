package org.example.timecapsule.repository;

import org.example.timecapsule.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    Optional<BoardEntity> findById(UUID id);

    void deleteById(UUID id);
}
