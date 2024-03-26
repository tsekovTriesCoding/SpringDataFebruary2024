package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "borrowing_records")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordsWrapperDto {
    @XmlElement(name = "borrowing_record")
    private List<BorrowingRecordImportDto> borrowingRecord;

    public BorrowingRecordsWrapperDto() {
    }

    public List<BorrowingRecordImportDto> getBorrowingRecord() {
        return borrowingRecord;
    }

    public void setBorrowingRecord(List<BorrowingRecordImportDto> borrowingRecord) {
        this.borrowingRecord = borrowingRecord;
    }
}
