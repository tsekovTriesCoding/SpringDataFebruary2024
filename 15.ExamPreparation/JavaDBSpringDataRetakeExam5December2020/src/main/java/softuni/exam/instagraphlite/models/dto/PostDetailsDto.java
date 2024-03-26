package softuni.exam.instagraphlite.models.dto;

import softuni.exam.instagraphlite.models.entity.Post;

public class PostDetailsDto {
    private String caption;
    private double pictureSize;

    public PostDetailsDto(String caption, double pictureSize) {
        this.caption = caption;
        this.pictureSize = pictureSize;
    }

    public PostDetailsDto(Post post) {
        this.caption = post.getCaption();
        this.pictureSize = post.getPicture().getSize();
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public double getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(double pictureSize) {
        this.pictureSize = pictureSize;
    }
}
