package softuni.exam.models.dto;

import softuni.exam.models.entity.Job;

public class JobExportDto {
    private final String jobTitle;
    private final double salary;
    private final double hoursAWeek;

    public JobExportDto(Job job) {
        this.jobTitle = job.getTitle();
        this.salary = job.getSalary();
        this.hoursAWeek = job.getHoursAWeek();
    }

    @Override
    public String toString() {
        return String.format("""
                Job title %s
                -Salary: %.2f$
                 --Hours a week: %.2fh.%n""", this.jobTitle, this.salary, this.hoursAWeek);
    }
}
