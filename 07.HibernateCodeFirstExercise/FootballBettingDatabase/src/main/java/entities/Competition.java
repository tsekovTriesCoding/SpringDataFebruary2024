package entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "competitions")
public class Competition extends BaseEntity {
    @ManyToOne
    private CompetitionType type;

    public CompetitionType getType() {
        return type;
    }

    public void setType(CompetitionType type) {
        this.type = type;
    }
}
