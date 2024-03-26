package softuni.exam.service;

import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.Genre;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface BorrowingRecordsService {

    boolean areImported();

    String readBorrowingRecordsFromFile() throws IOException;

	String importBorrowingRecords() throws IOException, JAXBException;

    String exportBorrowingRecords();

}
