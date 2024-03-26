package softuni.exam.models.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketImportDto {
    @Size(min = 2)
    @XmlElement(name = "serial-number")
    private String serialNumber;

    @Positive
    @XmlElement
    private BigDecimal price;

    @XmlElement(name = "take-off")
    private String takeoff;

    @XmlElement(name = "from-town")
    private TownNameDto fromTown;

    @XmlElement(name = "to-town")
    private TownNameDto toTown;

    @XmlElement
    private PassengerEmailDto passenger;

    @XmlElement
    private PlaneRegisterNumberDto plane;

    public TicketImportDto() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTakeoff() {
        return takeoff;
    }

    public void setTakeoff(String takeoff) {
        this.takeoff = takeoff;
    }

    public TownNameDto getFromTown() {
        return fromTown;
    }

    public void setFromTown(TownNameDto fromTown) {
        this.fromTown = fromTown;
    }

    public TownNameDto getToTown() {
        return toTown;
    }

    public void setToTown(TownNameDto toTown) {
        this.toTown = toTown;
    }

    public PassengerEmailDto getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerEmailDto passenger) {
        this.passenger = passenger;
    }

    public PlaneRegisterNumberDto getPlane() {
        return plane;
    }

    public void setPlane(PlaneRegisterNumberDto plane) {
        this.plane = plane;
    }
}
