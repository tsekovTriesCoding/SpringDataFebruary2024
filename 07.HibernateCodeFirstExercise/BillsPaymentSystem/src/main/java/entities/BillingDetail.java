package entities;

import javax.persistence.*;

@Entity(name = "billing_details")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BillingDetail {
    @Id
    @Column(unique = true, length = 30)
    private int number;
    @ManyToOne
    private User owner;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public User getUser() {
        return owner;
    }

    public void setUser(User user) {
        this.owner = user;
    }
}
