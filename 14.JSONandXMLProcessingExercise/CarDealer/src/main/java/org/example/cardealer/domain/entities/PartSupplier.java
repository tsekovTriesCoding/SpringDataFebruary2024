package org.example.cardealer.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Table(name = "part_suppliers")
public class PartSupplier extends BaseEntity{
    @Column
    private String name;
    @Column(name = "is_importer")
    private Boolean isImporter;
    @OneToMany(targetEntity = Part.class, mappedBy = "supplier")
    @Fetch(FetchMode.JOIN)
    private List<Part> parts;

    public PartSupplier() {
    }

    public PartSupplier(String name, Boolean isImporter, List<Part> parts) {
        this.name = name;
        this.isImporter = isImporter;
        this.parts = parts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsImporter() {
        return isImporter;
    }

    public void setIsImporter(Boolean isImporter) {
        this.isImporter = isImporter;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
