package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String capital;

    @OneToMany(mappedBy = "country")
    private List<Volcano> volcano;

    public Country() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public List<Volcano> getVolcano() {
        return volcano;
    }

    public void setVolcano(List<Volcano> volcano) {
        this.volcano = volcano;
    }
}
