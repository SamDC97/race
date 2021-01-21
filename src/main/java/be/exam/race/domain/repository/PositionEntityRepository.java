package be.exam.race.domain.repository;

import be.exam.race.domain.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionEntityRepository extends JpaRepository<PositionEntity, Long> {
}
