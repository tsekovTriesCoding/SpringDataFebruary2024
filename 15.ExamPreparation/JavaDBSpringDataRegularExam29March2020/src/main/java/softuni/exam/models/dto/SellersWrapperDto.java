package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sellers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellersWrapperDto {
    @XmlElement(name = "seller")
    private List<SellerImportDto> sellers;

    public SellersWrapperDto() {
    }

    public List<SellerImportDto> getSellers() {
        return sellers;
    }

    public void setSellers(List<SellerImportDto> sellers) {
        this.sellers = sellers;
    }
}
