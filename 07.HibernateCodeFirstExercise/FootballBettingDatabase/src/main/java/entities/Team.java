package entities;

import javax.persistence.*;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity {
    @Column
    private String logo;
    @Column
    private String initials;
    @ManyToOne
    @JoinColumn
    private Color primaryKitColor;
    @ManyToOne
    @JoinColumn
    private Color secondaryKitColor;
    @ManyToOne
    private Town town;
    @Column
    private double budget;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public Color getPrimaryKitColor() {
        return primaryKitColor;
    }

    public void setPrimaryKitColor(Color primaryKitColor) {
        this.primaryKitColor = primaryKitColor;
    }

    public Color getSecondaryKitColor() {
        return secondaryKitColor;
    }

    public void setSecondaryKitColor(Color secondaryKitColor) {
        this.secondaryKitColor = secondaryKitColor;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }
}
