package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BorrowingRecordImportDto;
import softuni.exam.models.dto.BorrowingRecordsWrapperDto;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.Genre;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.BORROWING_RECORD_PATH;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final LibraryMemberRepository libraryMemberRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;

    @Autowired
    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository,
                                       BookRepository bookRepository,
                                       LibraryMemberRepository libraryMemberRepository,
                                       ModelMapper modelMapper,
                                       ValidationUtils validationUtils,
                                       XmlParser xmlParser) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.libraryMemberRepository = libraryMemberRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.borrowingRecordRepository.count() > 0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return Files.readString(Path.of(BORROWING_RECORD_PATH));
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {
        BorrowingRecordsWrapperDto borrowingRecordsWrapperDto = this.xmlParser.fromFile(BORROWING_RECORD_PATH, BorrowingRecordsWrapperDto.class);

        StringBuilder sb = new StringBuilder();
        for (BorrowingRecordImportDto borrowingRecordImportDto : borrowingRecordsWrapperDto.getBorrowingRecord()) {
            boolean isValid = this.validationUtils.isValid(borrowingRecordImportDto);

            if (this.bookRepository.findByTitle(borrowingRecordImportDto.getBook().getTitle()).isEmpty() ||
                    this.libraryMemberRepository.findById(borrowingRecordImportDto.getMember().getId()).isEmpty()) {
                isValid = false;
            }

            if (isValid) {
                BorrowingRecord record = this.modelMapper.map(borrowingRecordImportDto, BorrowingRecord.class);
                Book book = this.bookRepository.findByTitle(borrowingRecordImportDto.getBook().getTitle()).get();
                LibraryMember libraryMember = this.libraryMemberRepository.findById(borrowingRecordImportDto.getMember().getId()).get();

                record.setBook(book);
                record.setLibraryMember(libraryMember);

                this.borrowingRecordRepository.saveAndFlush(record);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_RECORD, record.getBook().getTitle(), record.getBorrowDate()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_RECORD).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String exportBorrowingRecords() {
        List<BorrowingRecord> borrowingRecords = this.borrowingRecordRepository.findAllByBookGenreOrderByBorrowDateDesc(Genre.SCIENCE_FICTION)
                .orElseThrow(NoSuchElementException::new);

        StringBuilder sb = new StringBuilder();

        borrowingRecords.forEach(borrowRecord -> {
            sb.append(String.format("Book title: %s\n" +
                                    "*Book author: %s\n" +
                                    "**Date borrowed: %s\n" +
                                    "***Borrowed by: %s %s",
                            borrowRecord.getBook().getTitle(),
                            borrowRecord.getBook().getAuthor(),
                            borrowRecord.getBorrowDate().toString(),
                            borrowRecord.getLibraryMember().getFirstName(),
                            borrowRecord.getLibraryMember().getLastName()))
                    .append(System.lineSeparator());
        });

        return sb.toString().trim();
    }

}
