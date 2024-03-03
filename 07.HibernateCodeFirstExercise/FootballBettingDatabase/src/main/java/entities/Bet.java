package entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bets")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "bet_money")
    private double betMoney;
    @Column(name = "date_and_time_of_bet")
    private Date dateAndTimeOfBet;
    @ManyToOne
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(double betMoney) {
        this.betMoney = betMoney;
    }

    public Date getDateAndTimeOfBet() {
        return dateAndTimeOfBet;
    }

    public void setDateAndTimeOfBet(Date dateAndTimeOfBet) {
        this.dateAndTimeOfBet = dateAndTimeOfBet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
