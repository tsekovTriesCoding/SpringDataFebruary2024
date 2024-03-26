package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class XmlParserImpl implements XmlParser {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T fromFile(String filePath, Class<T> clazz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        unmarshaller.setAdapter(new LocalDateAdapter());

        return (T) unmarshaller.unmarshal(new File(filePath));
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
