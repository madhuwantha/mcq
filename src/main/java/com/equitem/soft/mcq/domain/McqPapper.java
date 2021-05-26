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
 * A McqPapper.
 */
@Document(collection = "mcq_papper")
public class McqPapper implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("time_in_min")
    private Integer timeInMin;

    @DBRef
    @Field("category")
    private Category category;

    @DBRef
    @Field("questions")
    @JsonIgnoreProperties(value = { "pappers" }, allowSetters = true)
    private Set<Question> questions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public McqPapper id(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public McqPapper title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTimeInMin() {
        return this.timeInMin;
    }

    public McqPapper timeInMin(Integer timeInMin) {
        this.timeInMin = timeInMin;
        return this;
    }

    public void setTimeInMin(Integer timeInMin) {
        this.timeInMin = timeInMin;
    }

    public Category getCategory() {
        return this.category;
    }

    public McqPapper category(Category category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Question> getQuestions() {
        return this.questions;
    }

    public McqPapper questions(Set<Question> questions) {
        this.setQuestions(questions);
        return this;
    }

    public McqPapper addQuestions(Question question) {
        this.questions.add(question);
        question.getPappers().add(this);
        return this;
    }

    public McqPapper removeQuestions(Question question) {
        this.questions.remove(question);
        question.getPappers().remove(this);
        return this;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof McqPapper)) {
            return false;
        }
        return id != null && id.equals(((McqPapper) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "McqPapper{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", timeInMin=" + getTimeInMin() +
            "}";
    }
}
