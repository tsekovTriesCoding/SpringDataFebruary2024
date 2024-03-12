package org.example.jsonprocessingexercise.constants;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public enum Utils {
    ;
    public static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public static final Gson GSON = new Gson().newBuilder().setPrettyPrinting().create();

    public static void writeJsonOnToFile(List<?> objects, Path filePath) throws IOException {
        final FileWriter fileWriter = new FileWriter(filePath.toFile());

        GSON.toJson(objects, fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public static <T> void writeXmlOnToFile(T data, Path filePath) throws JAXBException {
        final JAXBContext context = JAXBContext.newInstance(data.getClass());
        final Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        File file = filePath.toFile();
        marshaller.marshal(data, file);
    }

    public static void writeJsonOnToFile(Object object, Path filePath) throws IOException {
        final FileWriter fileWriter = new FileWriter(filePath.toFile());

        GSON.toJson(object, fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }
}
