package softuni.exam.models.dto;

import softuni.exam.util.XmlParser;
import softuni.exam.util.XmlParserImpl;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "borrowing_record")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordImportDto {
    @NotNull
    @XmlJavaTypeAdapter(XmlParserImpl.LocalDateAdapter.class)
    @XmlElement(name = "borrow_date")
    private LocalDate borrowDate;

    @NotNull
    @XmlJavaTypeAdapter(XmlParserImpl.LocalDateAdapter.class)
    @XmlElement(name = "return_date")
    private LocalDate returnDate;

    @XmlElement
    private BookTitleDto book;

    @XmlElement
    private MemberIdDto member;

    @Size(min = 3, max = 100)
    @XmlElement
    private String remarks;

    public BorrowingRecordImportDto() {
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public BookTitleDto getBook() {
        return book;
    }

    public void setBook(BookTitleDto book) {
        this.book = book;
    }

    public MemberIdDto getMember() {
        return member;
    }

    public void setMember(MemberIdDto member) {
        this.member = member;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
