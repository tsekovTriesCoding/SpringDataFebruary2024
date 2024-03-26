package softuni.exam.instagraphlite.models.dto;

import softuni.exam.instagraphlite.models.entity.User;

import java.util.Comparator;
import java.util.List;

public class UserWithPostExportDto {
    private String username;
    private int countOfPosts;

    private List<PostDetailsDto> posts;

    public UserWithPostExportDto(String username, int countOfPosts, List<PostDetailsDto> posts) {
        this.username = username;
        this.countOfPosts = countOfPosts;
        this.posts = posts;
    }

    public UserWithPostExportDto(User user) {
        this.username = user.getUsername();
        this.countOfPosts = user.getPosts().size();

        this.posts = user.getPosts()
                .stream()
                .sorted(Comparator.comparingDouble(p -> p.getPicture().getSize()))
                .map(PostDetailsDto::new)
                .toList();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCountOfPosts() {
        return countOfPosts;
    }

    public void setCountOfPosts(int countOfPosts) {
        this.countOfPosts = countOfPosts;
    }

    public List<PostDetailsDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDetailsDto> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("User: %s", this.username)).append(System.lineSeparator());
        sb.append(String.format("Post count: %d", this.countOfPosts)).append(System.lineSeparator());
        sb.append("==Post Details:").append(System.lineSeparator());

        this.posts.forEach(p -> sb.append(String.format("----Caption: %s", p.getCaption())).append(System.lineSeparator())
                .append(String.format("----Picture Size: %.2f", p.getPictureSize())).append(System.lineSeparator()));

        return sb.toString().trim();
    }
}
