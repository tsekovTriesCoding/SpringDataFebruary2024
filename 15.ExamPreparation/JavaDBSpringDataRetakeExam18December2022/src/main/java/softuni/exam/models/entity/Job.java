package softuni.exam.models.entity;

import softuni.exam.models.dto.JobExportDto;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "jobs")
public class Job extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private double salary;

    @Column(nullable = false)
    private double hoursAWeek;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @ManyToOne(cascade = ALL)
    private Company company;

    public Job() {
    }

    public static JobExportDto toJobExportDto(Job job) {
        return new JobExportDto(job);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getHoursAWeek() {
        return hoursAWeek;
    }

    public void setHoursAWeek(double hoursAWeek) {
        this.hoursAWeek = hoursAWeek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
