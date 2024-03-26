package exam.model.dto;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "shop")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopImportDto {
    @NotNull
    @Size(min = 4)
    @XmlElement
    private String address;

    @NotNull
    @Min(1)
    @Max(50)
    @XmlElement(name = "employee-count")
    private int employeeCount;

    @NotNull
    @DecimalMin("20000")
    @XmlElement
    private BigDecimal income;

    @NotNull
    @Size(min = 4)
    @XmlElement
    private String name;

    @NotNull
    @Min(150)
    @XmlElement(name = "shop-area")
    private int shopArea;

    @XmlElement
    private TownNameDto town;

    public ShopImportDto() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShopArea() {
        return shopArea;
    }

    public void setShopArea(int shopArea) {
        this.shopArea = shopArea;
    }

    public TownNameDto getTown() {
        return town;
    }

    public void setTown(TownNameDto town) {
        this.town = town;
    }
}
