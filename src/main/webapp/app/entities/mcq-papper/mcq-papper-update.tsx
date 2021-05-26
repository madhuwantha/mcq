import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { IQuestion } from 'app/shared/model/question.model';
import { getEntities as getQuestions } from 'app/entities/question/question.reducer';
import { getEntity, updateEntity, createEntity, reset } from './mcq-papper.reducer';
import { IMcqPapper } from 'app/shared/model/mcq-papper.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMcqPapperUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const McqPapperUpdate = (props: IMcqPapperUpdateProps) => {
  const [idsquestions, setIdsquestions] = useState([]);
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { mcqPapperEntity, categories, questions, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/mcq-papper');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCategories();
    props.getQuestions();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...mcqPapperEntity,
        ...values,
        questions: mapIdList(values.questions),
        category: categories.find(it => it.id.toString() === values.categoryId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mcqApp.mcqPapper.home.createOrEditLabel" data-cy="McqPapperCreateUpdateHeading">
            <Translate contentKey="mcqApp.mcqPapper.home.createOrEditLabel">Create or edit a McqPapper</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : mcqPapperEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="mcq-papper-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="mcq-papper-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="mcq-papper-title">
                  <Translate contentKey="mcqApp.mcqPapper.title">Title</Translate>
                </Label>
                <AvField id="mcq-papper-title" data-cy="title" type="text" name="title" />
              </AvGroup>
              <AvGroup>
                <Label id="timeInMinLabel" for="mcq-papper-timeInMin">
                  <Translate contentKey="mcqApp.mcqPapper.timeInMin">Time In Min</Translate>
                </Label>
                <AvField id="mcq-papper-timeInMin" data-cy="timeInMin" type="string" className="form-control" name="timeInMin" />
              </AvGroup>
              <AvGroup>
                <Label for="mcq-papper-category">
                  <Translate contentKey="mcqApp.mcqPapper.category">Category</Translate>
                </Label>
                <AvInput id="mcq-papper-category" data-cy="category" type="select" className="form-control" name="categoryId">
                  <option value="" key="0" />
                  {categories
                    ? categories.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="mcq-papper-questions">
                  <Translate contentKey="mcqApp.mcqPapper.questions">Questions</Translate>
                </Label>
                <AvInput
                  id="mcq-papper-questions"
                  data-cy="questions"
                  type="select"
                  multiple
                  className="form-control"
                  name="questions"
                  value={!isNew && mcqPapperEntity.questions && mcqPapperEntity.questions.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {questions
                    ? questions.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/mcq-papper" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  categories: storeState.category.entities,
  questions: storeState.question.entities,
  mcqPapperEntity: storeState.mcqPapper.entity,
  loading: storeState.mcqPapper.loading,
  updating: storeState.mcqPapper.updating,
  updateSuccess: storeState.mcqPapper.updateSuccess,
});

const mapDispatchToProps = {
  getCategories,
  getQuestions,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(McqPapperUpdate);
