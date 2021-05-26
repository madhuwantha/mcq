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
 * A Question.
 */
@Document(collection = "question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("code")
    private String code;

    @Field("question_image_link")
    private String questionImageLink;

    @Field("answer_image_liks")
    private String answerImageLiks;

    @Field("correct_one")
    private Integer correctOne;

    @DBRef
    @Field("pappers")
    @JsonIgnoreProperties(value = { "questions", "categories" }, allowSetters = true)
    private Set<McqPapper> pappers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Question id(String id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Question code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQuestionImageLink() {
        return this.questionImageLink;
    }

    public Question questionImageLink(String questionImageLink) {
        this.questionImageLink = questionImageLink;
        return this;
    }

    public void setQuestionImageLink(String questionImageLink) {
        this.questionImageLink = questionImageLink;
    }

    public String getAnswerImageLiks() {
        return this.answerImageLiks;
    }

    public Question answerImageLiks(String answerImageLiks) {
        this.answerImageLiks = answerImageLiks;
        return this;
    }

    public void setAnswerImageLiks(String answerImageLiks) {
        this.answerImageLiks = answerImageLiks;
    }

    public Integer getCorrectOne() {
        return this.correctOne;
    }

    public Question correctOne(Integer correctOne) {
        this.correctOne = correctOne;
        return this;
    }

    public void setCorrectOne(Integer correctOne) {
        this.correctOne = correctOne;
    }

    public Set<McqPapper> getPappers() {
        return this.pappers;
    }

    public Question pappers(Set<McqPapper> mcqPappers) {
        this.setPappers(mcqPappers);
        return this;
    }

    public Question addPappers(McqPapper mcqPapper) {
        this.pappers.add(mcqPapper);
        mcqPapper.getQuestions().add(this);
        return this;
    }

    public Question removePappers(McqPapper mcqPapper) {
        this.pappers.remove(mcqPapper);
        mcqPapper.getQuestions().remove(this);
        return this;
    }

    public void setPappers(Set<McqPapper> mcqPappers) {
        if (this.pappers != null) {
            this.pappers.forEach(i -> i.removeQuestions(this));
        }
        if (mcqPappers != null) {
            mcqPappers.forEach(i -> i.addQuestions(this));
        }
        this.pappers = mcqPappers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        return id != null && id.equals(((Question) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", questionImageLink='" + getQuestionImageLink() + "'" +
            ", answerImageLiks='" + getAnswerImageLiks() + "'" +
            ", correctOne=" + getCorrectOne() +
            "}";
    }
}
