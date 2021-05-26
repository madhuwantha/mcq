import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './attempt.reducer';
import { IAttempt } from 'app/shared/model/attempt.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAttemptUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AttemptUpdate = (props: IAttemptUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { attemptEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/attempt');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...attemptEntity,
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
          <h2 id="mcqApp.attempt.home.createOrEditLabel" data-cy="AttemptCreateUpdateHeading">
            <Translate contentKey="mcqApp.attempt.home.createOrEditLabel">Create or edit a Attempt</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : attemptEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="attempt-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="attempt-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="studendIdLabel" for="attempt-studendId">
                  <Translate contentKey="mcqApp.attempt.studendId">Studend Id</Translate>
                </Label>
                <AvField id="attempt-studendId" data-cy="studendId" type="text" name="studendId" />
              </AvGroup>
              <AvGroup>
                <Label id="papperIdLabel" for="attempt-papperId">
                  <Translate contentKey="mcqApp.attempt.papperId">Papper Id</Translate>
                </Label>
                <AvField id="attempt-papperId" data-cy="papperId" type="text" name="papperId" />
              </AvGroup>
              <AvGroup>
                <Label id="attemptNoLabel" for="attempt-attemptNo">
                  <Translate contentKey="mcqApp.attempt.attemptNo">Attempt No</Translate>
                </Label>
                <AvField id="attempt-attemptNo" data-cy="attemptNo" type="string" className="form-control" name="attemptNo" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/attempt" replace color="info">
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
  attemptEntity: storeState.attempt.entity,
  loading: storeState.attempt.loading,
  updating: storeState.attempt.updating,
  updateSuccess: storeState.attempt.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AttemptUpdate);
