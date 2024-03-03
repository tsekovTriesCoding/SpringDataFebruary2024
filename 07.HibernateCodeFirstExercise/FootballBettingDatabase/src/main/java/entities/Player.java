package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "players")
public class Player extends BaseEntity {
    @Column(name = "squad_number")
    private short squadNumber;
    @ManyToOne
    private Team team;
    @ManyToOne
    private Position position;
    @Column(name = "is_currently_injured")
    private boolean isCurrentlyInjured;

    public short getSquadNumber() {
        return squadNumber;
    }

    public void setSquadNumber(short squadNumber) {
        this.squadNumber = squadNumber;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isCurrentlyInjured() {
        return isCurrentlyInjured;
    }

    public void setCurrentlyInjured(boolean currentlyInjured) {
        isCurrentlyInjured = currentlyInjured;
    }
}
