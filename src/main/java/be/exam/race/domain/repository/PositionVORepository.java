package be.exam.race.domain.repository;

import be.exam.race.domain.PositionVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionVORepository extends JpaRepository<PositionVO, Long> {
}
