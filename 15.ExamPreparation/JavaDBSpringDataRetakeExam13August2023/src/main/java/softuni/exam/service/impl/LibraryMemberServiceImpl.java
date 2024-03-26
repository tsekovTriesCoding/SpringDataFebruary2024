package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.LibraryMemberImportDto;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.Messages.INVALID_MEMBER;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_MEMBER;
import static softuni.exam.constants.Paths.LIBRARY_MEMBERS_PATH;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {
    private final LibraryMemberRepository libraryMemberRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository,
                                    Gson gson,
                                    ValidationUtils validationUtils,
                                    ModelMapper modelMapper) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.libraryMemberRepository.count() > 0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return Files.readString(Path.of(LIBRARY_MEMBERS_PATH));
    }

    @Override
    public String importLibraryMembers() throws IOException {
        LibraryMemberImportDto[] libraryMemberImportDtos = this.gson.fromJson(this.readLibraryMembersFileContent(), LibraryMemberImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (LibraryMemberImportDto libraryMemberImportDto : libraryMemberImportDtos) {
            boolean isValid = this.validationUtils.isValid(libraryMemberImportDto);

            if (this.libraryMemberRepository.findByPhoneNumber(libraryMemberImportDto.getPhoneNumber()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                LibraryMember member = this.modelMapper.map(libraryMemberImportDto, LibraryMember.class);

                this.libraryMemberRepository.saveAndFlush(member);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_MEMBER, member.getFirstName(), member.getLastName()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_MEMBER).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
