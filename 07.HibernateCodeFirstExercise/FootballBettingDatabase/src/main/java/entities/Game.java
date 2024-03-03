package entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn
    private Team homeTeam;
    @OneToOne
    @JoinColumn
    private Team awayTeam;
    @Column(name = "home_goals")
    private short homeGoals;
    @Column(name = "away_goals")
    private short awayGoals;
    @Column(name = "date_and_time_of_game")
    private Date dateAndTimeOfGame;
    @Column(name = "home_team_win_bet_rate")
    private double homeTeamWinBetRate;
    @Column(name = "away_team_win_bet_rate")
    private double awayTeamWinBetRate;
    @Column(name = "draw_gamebet_rate")
    private double drawGameBetRate;
    @ManyToOne
    @JoinColumn
    private Round round;
    @ManyToOne
    @JoinColumn
    private Competition competition;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public short getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(short homeGoals) {
        this.homeGoals = homeGoals;
    }

    public short getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(short awayGoals) {
        this.awayGoals = awayGoals;
    }

    public Date getDateAndTimeOfGame() {
        return dateAndTimeOfGame;
    }

    public void setDateAndTimeOfGame(Date dateAndTimeOfGame) {
        this.dateAndTimeOfGame = dateAndTimeOfGame;
    }

    public double getHomeTeamWinBetRate() {
        return homeTeamWinBetRate;
    }

    public void setHomeTeamWinBetRate(double homeTeamWinBetRate) {
        this.homeTeamWinBetRate = homeTeamWinBetRate;
    }

    public double getAwayTeamWinBetRate() {
        return awayTeamWinBetRate;
    }

    public void setAwayTeamWinBetRate(double awayTeamWinBetRate) {
        this.awayTeamWinBetRate = awayTeamWinBetRate;
    }

    public double getDrawGameBetRate() {
        return drawGameBetRate;
    }

    public void setDrawGameBetRate(double drawGameBetRate) {
        this.drawGameBetRate = drawGameBetRate;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}
