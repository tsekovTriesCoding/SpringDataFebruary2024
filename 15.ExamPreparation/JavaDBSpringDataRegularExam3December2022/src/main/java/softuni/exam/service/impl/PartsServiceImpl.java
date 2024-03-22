package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PartImportDto;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartsRepository;
import softuni.exam.service.PartsService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Locale;

import static softuni.exam.constants.Messages.INVALID_PART;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_PART;
import static softuni.exam.constants.Paths.PARTS_PATH;

@Service
public class PartsServiceImpl implements PartsService {
    private final PartsRepository partsRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public PartsServiceImpl(PartsRepository partsRepository,
                            Gson gson,
                            ValidationUtils validationUtils,
                            ModelMapper modelMapper) {
        this.partsRepository = partsRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.partsRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(Path.of(PARTS_PATH));
    }

    @Override
    public String importParts() throws IOException {
        PartImportDto[] partImportDtos = this.gson.fromJson(this.readPartsFileContent(), PartImportDto[].class);

        StringBuilder sb = new StringBuilder();
        Locale.setDefault(Locale.US);
        for (PartImportDto partImportDto : partImportDtos) {
            boolean isValid = this.validationUtils.isValid(partImportDto);

            if (this.partsRepository.findByPartName(partImportDto.getPartName()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Part part = this.modelMapper.map(partImportDto, Part.class);

                DecimalFormat df = new DecimalFormat("#####.0#");
                this.partsRepository.saveAndFlush(part);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_PART, part.getPartName(), df.format(part.getPrice())))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_PART).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
