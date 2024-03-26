package exam.model.dto;

import java.math.BigDecimal;

public class BestLaptopDto {
    private String macAddress;
    private double cpuSpeed;
    private int ram;
    private int storage;
    private BigDecimal price;
    private String shopName;
    private String townName;

    public BestLaptopDto(String macAddress, double cpuSpeed, int ram, int storage, BigDecimal price, String shopName, String townName) {
        this.macAddress = macAddress;
        this.cpuSpeed = cpuSpeed;
        this.ram = ram;
        this.storage = storage;
        this.price = price;
        this.shopName = shopName;
        this.townName = townName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public double getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(double cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    @Override
    public String toString() {
        return String.format("""
                Laptop - %s
                *Cpu speed - %.2f
                **Ram - %d
                ***Storage - %d
                ****Price - %.2f
                #Shop name - %s
                ##Town - %s
                """, this.macAddress, this.cpuSpeed, this.ram, this.storage, this.price, this.shopName, this.townName);
    }
}
