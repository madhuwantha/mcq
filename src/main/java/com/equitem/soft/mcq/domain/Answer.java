package com.equitem.soft.mcq.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Answer.
 */
@Document(collection = "answer")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("question_id")
    private String questionId;

    @Field("answer")
    private Integer answer;

    @Field("status")
    private Boolean status;

    @DBRef
    @Field("attempt")
    @JsonIgnoreProperties(value = { "answers" }, allowSetters = true)
    private Attempt attempt;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Answer id(String id) {
        this.id = id;
        return this;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public Answer questionId(String questionId) {
        this.questionId = questionId;
        return this;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Integer getAnswer() {
        return this.answer;
    }

    public Answer answer(Integer answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Answer status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Attempt getAttempt() {
        return this.attempt;
    }

    public Answer attempt(Attempt attempt) {
        this.setAttempt(attempt);
        return this;
    }

    public void setAttempt(Attempt attempt) {
        this.attempt = attempt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Answer)) {
            return false;
        }
        return id != null && id.equals(((Answer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Answer{" +
            "id=" + getId() +
            ", questionId='" + getQuestionId() + "'" +
            ", answer=" + getAnswer() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
