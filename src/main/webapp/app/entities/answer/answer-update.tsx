import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAttempt } from 'app/shared/model/attempt.model';
import { getEntities as getAttempts } from 'app/entities/attempt/attempt.reducer';
import { getEntity, updateEntity, createEntity, reset } from './answer.reducer';
import { IAnswer } from 'app/shared/model/answer.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAnswerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AnswerUpdate = (props: IAnswerUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { answerEntity, attempts, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/answer');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAttempts();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...answerEntity,
        ...values,
        attempt: attempts.find(it => it.id.toString() === values.attemptId.toString()),
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
          <h2 id="mcqApp.answer.home.createOrEditLabel" data-cy="AnswerCreateUpdateHeading">
            <Translate contentKey="mcqApp.answer.home.createOrEditLabel">Create or edit a Answer</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : answerEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="answer-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="answer-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="questionIdLabel" for="answer-questionId">
                  <Translate contentKey="mcqApp.answer.questionId">Question Id</Translate>
                </Label>
                <AvField id="answer-questionId" data-cy="questionId" type="text" name="questionId" />
              </AvGroup>
              <AvGroup>
                <Label id="answerLabel" for="answer-answer">
                  <Translate contentKey="mcqApp.answer.answer">Answer</Translate>
                </Label>
                <AvField id="answer-answer" data-cy="answer" type="string" className="form-control" name="answer" />
              </AvGroup>
              <AvGroup check>
                <Label id="statusLabel">
                  <AvInput id="answer-status" data-cy="status" type="checkbox" className="form-check-input" name="status" />
                  <Translate contentKey="mcqApp.answer.status">Status</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="answer-attempt">
                  <Translate contentKey="mcqApp.answer.attempt">Attempt</Translate>
                </Label>
                <AvInput id="answer-attempt" data-cy="attempt" type="select" className="form-control" name="attemptId">
                  <option value="" key="0" />
                  {attempts
                    ? attempts.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/answer" replace color="info">
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
  attempts: storeState.attempt.entities,
  answerEntity: storeState.answer.entity,
  loading: storeState.answer.loading,
  updating: storeState.answer.updating,
  updateSuccess: storeState.answer.updateSuccess,
});

const mapDispatchToProps = {
  getAttempts,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AnswerUpdate);
