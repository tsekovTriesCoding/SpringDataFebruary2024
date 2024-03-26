package softuni.exam.instagraphlite.models.dto;

public class PictureExportDto {
    private double size;
    private String path;

    public PictureExportDto() {
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return String.format("%.2f - %s", this.size, this.path);
    }
}
