package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "jobs")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobsWrapperDto {
    @XmlElement(name = "job")
    private List<JobImportDto> jobs;

    public JobsWrapperDto() {
    }

    public List<JobImportDto> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobImportDto> jobs) {
        this.jobs = jobs;
    }
}
