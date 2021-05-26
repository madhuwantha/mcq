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
 * A Attempt.
 */
@Document(collection = "attempt")
public class Attempt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("studend_id")
    private String studendId;

    @Field("papper_id")
    private String papperId;

    @Field("attempt_no")
    private Integer attemptNo;

    @DBRef
    @Field("answers")
    @JsonIgnoreProperties(value = { "attempt" }, allowSetters = true)
    private Set<Answer> answers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Attempt id(String id) {
        this.id = id;
        return this;
    }

    public String getStudendId() {
        return this.studendId;
    }

    public Attempt studendId(String studendId) {
        this.studendId = studendId;
        return this;
    }

    public void setStudendId(String studendId) {
        this.studendId = studendId;
    }

    public String getPapperId() {
        return this.papperId;
    }

    public Attempt papperId(String papperId) {
        this.papperId = papperId;
        return this;
    }

    public void setPapperId(String papperId) {
        this.papperId = papperId;
    }

    public Integer getAttemptNo() {
        return this.attemptNo;
    }

    public Attempt attemptNo(Integer attemptNo) {
        this.attemptNo = attemptNo;
        return this;
    }

    public void setAttemptNo(Integer attemptNo) {
        this.attemptNo = attemptNo;
    }

    public Set<Answer> getAnswers() {
        return this.answers;
    }

    public Attempt answers(Set<Answer> answers) {
        this.setAnswers(answers);
        return this;
    }

    public Attempt addAnswers(Answer answer) {
        this.answers.add(answer);
        answer.setAttempt(this);
        return this;
    }

    public Attempt removeAnswers(Answer answer) {
        this.answers.remove(answer);
        answer.setAttempt(null);
        return this;
    }

    public void setAnswers(Set<Answer> answers) {
        if (this.answers != null) {
            this.answers.forEach(i -> i.setAttempt(null));
        }
        if (answers != null) {
            answers.forEach(i -> i.setAttempt(this));
        }
        this.answers = answers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attempt)) {
            return false;
        }
        return id != null && id.equals(((Attempt) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attempt{" +
            "id=" + getId() +
            ", studendId='" + getStudendId() + "'" +
            ", papperId='" + getPapperId() + "'" +
            ", attemptNo=" + getAttemptNo() +
            "}";
    }
}
