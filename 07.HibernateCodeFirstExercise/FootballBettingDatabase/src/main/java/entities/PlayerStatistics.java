package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "player_statistics")
public class PlayerStatistics implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn
    private Game game;
    @Id
    @ManyToOne
    @JoinColumn
    private Player player;
    @Column(name = "scored_goals")
    private short scoredGoals;
    @Column(name = "player_assists")
    private short playerAssists;
    @Column(name = "played_minutes_during_game")
    private short playedMinutesDuringGame;

    public PlayerStatistics() {
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public short getScoredGoals() {
        return scoredGoals;
    }

    public void setScoredGoals(short scoredGoals) {
        this.scoredGoals = scoredGoals;
    }

    public short getPlayerAssists() {
        return playerAssists;
    }

    public void setPlayerAssists(short playerAssists) {
        this.playerAssists = playerAssists;
    }

    public short getPlayedMinutesDuringGame() {
        return playedMinutesDuringGame;
    }

    public void setPlayedMinutesDuringGame(short playedMinutesDuringGame) {
        this.playedMinutesDuringGame = playedMinutesDuringGame;
    }

}
