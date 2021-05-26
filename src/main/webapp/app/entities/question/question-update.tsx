import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMcqPapper } from 'app/shared/model/mcq-papper.model';
import { getEntities as getMcqPappers } from 'app/entities/mcq-papper/mcq-papper.reducer';
import { getEntity, updateEntity, createEntity, reset } from './question.reducer';
import { IQuestion } from 'app/shared/model/question.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IQuestionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const QuestionUpdate = (props: IQuestionUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { questionEntity, mcqPappers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/question');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMcqPappers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...questionEntity,
        ...values,
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
          <h2 id="mcqApp.question.home.createOrEditLabel" data-cy="QuestionCreateUpdateHeading">
            <Translate contentKey="mcqApp.question.home.createOrEditLabel">Create or edit a Question</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : questionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="question-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="question-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="question-code">
                  <Translate contentKey="mcqApp.question.code">Code</Translate>
                </Label>
                <AvField id="question-code" data-cy="code" type="text" name="code" />
              </AvGroup>
              <AvGroup>
                <Label id="questionImageLinkLabel" for="question-questionImageLink">
                  <Translate contentKey="mcqApp.question.questionImageLink">Question Image Link</Translate>
                </Label>
                <AvField id="question-questionImageLink" data-cy="questionImageLink" type="text" name="questionImageLink" />
              </AvGroup>
              <AvGroup>
                <Label id="answerImageLiksLabel" for="question-answerImageLiks">
                  <Translate contentKey="mcqApp.question.answerImageLiks">Answer Image Liks</Translate>
                </Label>
                <AvField id="question-answerImageLiks" data-cy="answerImageLiks" type="text" name="answerImageLiks" />
              </AvGroup>
              <AvGroup>
                <Label id="correctOneLabel" for="question-correctOne">
                  <Translate contentKey="mcqApp.question.correctOne">Correct One</Translate>
                </Label>
                <AvField id="question-correctOne" data-cy="correctOne" type="string" className="form-control" name="correctOne" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/question" replace color="info">
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
  mcqPappers: storeState.mcqPapper.entities,
  questionEntity: storeState.question.entity,
  loading: storeState.question.loading,
  updating: storeState.question.updating,
  updateSuccess: storeState.question.updateSuccess,
});

const mapDispatchToProps = {
  getMcqPappers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QuestionUpdate);
