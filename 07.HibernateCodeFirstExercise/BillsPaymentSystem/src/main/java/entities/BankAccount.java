package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name =  "bank_accounts")
public class BankAccount extends BillingDetail {
    @Column(name = "bank_name", length = 50,nullable = false)
    private String bankName;

    @Column(name = "SWIFT_code", length = 50,nullable = false)
    private String swiftCode;

    public BankAccount() {
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}
