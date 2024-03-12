package org.example.cardealer.domain.dtos.supplier;

import org.example.cardealer.domain.entities.Part;

import java.util.Set;

public class SupplierDto {
    private long id;
    private String name;
    private Boolean isImporter;
    private Set<Part> parts;

    public SupplierDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getImporter() {
        return isImporter;
    }

    public void setImporter(Boolean importer) {
        isImporter = importer;
    }

    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalSupplierDto toLocalSupplierDto() {
        return new LocalSupplierDto(this.id ,this.name, this.parts);
    }

}
