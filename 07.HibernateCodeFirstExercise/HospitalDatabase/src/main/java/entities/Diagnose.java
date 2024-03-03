package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "diagnoses")
public class Diagnose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 50, nullable = false)
    private String name;
    @OneToMany(mappedBy = "diagnose")
    private Set<Comment> comments;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Diagnose: ").append(this.name).append(System.lineSeparator());
        sb.append("Comments: ");
        this.comments.forEach(c -> sb.append(c).append(System.lineSeparator()));
        return sb.toString().trim();
    }
}
