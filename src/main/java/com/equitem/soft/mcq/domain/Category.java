package com.equitem.soft.mcq.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Category.
 */
@Document(collection = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("code")
    private String code;

    @DBRef
    @Field("mcqqPappers")
    @JsonIgnoreProperties(value = { "questions", "categories" }, allowSetters = true)
    private Set<McqPapper> mcqqPappers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category id(String id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Category code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<McqPapper> getMcqqPappers() {
        return this.mcqqPappers;
    }

    public Category mcqqPappers(Set<McqPapper> mcqPappers) {
        this.setMcqqPappers(mcqPappers);
        return this;
    }

    public Category addMcqqPappers(McqPapper mcqPapper) {
        this.mcqqPappers.add(mcqPapper);
        mcqPapper.getCategories().add(this);
        return this;
    }

    public Category removeMcqqPappers(McqPapper mcqPapper) {
        this.mcqqPappers.remove(mcqPapper);
        mcqPapper.getCategories().remove(this);
        return this;
    }

    public void setMcqqPappers(Set<McqPapper> mcqPappers) {
        this.mcqqPappers = mcqPappers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            "}";
    }
}
