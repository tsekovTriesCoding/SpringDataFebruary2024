package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class XmlParser {
    public <T> T fromFile(File file, Class<T> object) throws JAXBException, FileNotFoundException {
        final JAXBContext context = JAXBContext.newInstance(object);
        final Unmarshaller unmarshaller = context.createUnmarshaller();

        unmarshaller.setAdapter(new LocalDateAdapter());
        final FileReader fileReader = new FileReader(file);

        return (T) unmarshaller.unmarshal(fileReader);
    }

    public static class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public LocalDate unmarshal(String s) throws Exception {
            return LocalDate.parse(s, formatter);
        }

        @Override
        public String marshal(LocalDate localDate) throws Exception {
            return localDate.format(formatter);
        }
    }
}
