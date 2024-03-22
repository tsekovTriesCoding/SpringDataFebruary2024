package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TaskImportDto;
import softuni.exam.models.dto.TasksExportDto;
import softuni.exam.models.dto.TasksWrapperDto;
import softuni.exam.models.entity.CarType;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.models.entity.Task;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.repository.PartsRepository;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.TasksService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_TASK;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_TASK;

@Service
public class TasksServiceImpl implements TasksService {
    private static String TASKS_FILE_PATH = "src/main/resources/files/xml/tasks.xml";
    private final TasksRepository tasksRepository;
    private final MechanicsRepository mechanicsRepository;
    private final PartsRepository partsRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;

    @Autowired
    public TasksServiceImpl(TasksRepository tasksRepository,
                            MechanicsRepository mechanicsRepository,
                            PartsRepository partsRepository,
                            ModelMapper modelMapper,
                            ValidationUtils validationUtils,
                            XmlParser xmlParser) {
        this.tasksRepository = tasksRepository;
        this.mechanicsRepository = mechanicsRepository;
        this.partsRepository = partsRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.tasksRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(Path.of(TASKS_FILE_PATH));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        TasksWrapperDto tasksWrapperDto = this.xmlParser.fromFile(TASKS_FILE_PATH, TasksWrapperDto.class);

        Locale.setDefault(Locale.US);
        StringBuilder sb = new StringBuilder();
        for (TaskImportDto taskImportDto : tasksWrapperDto.getTasks()) {
            boolean isValid = this.validationUtils.isValid(taskImportDto);

            if (this.mechanicsRepository.findByFirstName(taskImportDto.getMechanic().getFirstName()).isEmpty()) {
                isValid = false;
            }

            if (isValid) {
                Task task = this.modelMapper.map(taskImportDto, Task.class);

                Mechanic mechanic = this.mechanicsRepository.findByFirstName(taskImportDto.getMechanic().getFirstName()).get();

                Part part = this.partsRepository.findById(taskImportDto.getPart().getId()).get();
                task.setMechanic(mechanic);
                task.setPart(part);

                this.tasksRepository.saveAndFlush(task);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_TASK, task.getPrice()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_TASK).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        addMappingsForExport();

        String exportInfo = this.tasksRepository.findAllByCarCarTypeOrderByPriceDesc(CarType.coupe)
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(task -> this.modelMapper.map(task, TasksExportDto.class))
                .map(TasksExportDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        return exportInfo.trim();
    }

    private void addMappingsForExport() {
        TypeMap<Task, TasksExportDto> propertyMapper = this.modelMapper.createTypeMap(Task.class, TasksExportDto.class);

        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getCar().getKilometers(), TasksExportDto::setKilometers)
        );
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getMechanic().getFirstName(), TasksExportDto::setFirstName)
        );
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getMechanic().getLastName(), TasksExportDto::setLastName)
        );
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getMechanic().getLastName(), TasksExportDto::setLastName)
        );
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getCar().getEngine(), TasksExportDto::setEngine)
        );
    }
}
