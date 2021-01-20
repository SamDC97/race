package be.exam.race.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceEntity {

    @Id
    private Long id;
    @Embedded()
    private List<PositionVO> positionVO;
}
