package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class XmlParser {
    public <T> T fromFile(File file, Class<T> object) throws JAXBException, FileNotFoundException {
        final JAXBContext context = JAXBContext.newInstance(object);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setAdapter(new LocalTimeAdapter());

        final FileReader fileReader = new FileReader(file);

        return (T) unmarshaller.unmarshal(fileReader);
    }

    public static class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        @Override
        public LocalTime unmarshal(String s) throws Exception {
            return LocalTime.parse(s, formatter);
        }

        @Override
        public String marshal(LocalTime localTime) throws Exception {
            return localTime.format(formatter);
        }
    }

}
