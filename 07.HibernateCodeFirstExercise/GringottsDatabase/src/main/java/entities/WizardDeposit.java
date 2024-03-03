package entities;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "wizard_deposits")
public class WizardDeposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name", length = 50)
    private String firstName;
    @Column(name = "last_name", length = 60, nullable = false)
    private String lastName;
    @Column(length = 1000)
    private String notes;
    @Column(nullable = false)
    private Integer age;
    @Column(length = 100)
    private String magicWandCreator;
    @Column
    private Integer magicWandSize;
    @Column(length = 20)
    private String depositGroup;
    @Column
    private Date depositStartDate;
    @Column
    private Double depositAmount;
    @Column
    private Double depositInterest;
    @Column
    private Double depositCharge;
    @Column
    private Date depositExpirationDate;
    @Column
    private Boolean isDepositExpired;
}
