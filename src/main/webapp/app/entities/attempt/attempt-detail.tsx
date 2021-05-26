import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './attempt.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAttemptDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AttemptDetail = (props: IAttemptDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { attemptEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attemptDetailsHeading">
          <Translate contentKey="mcqApp.attempt.detail.title">Attempt</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{attemptEntity.id}</dd>
          <dt>
            <span id="studendId">
              <Translate contentKey="mcqApp.attempt.studendId">Studend Id</Translate>
            </span>
          </dt>
          <dd>{attemptEntity.studendId}</dd>
          <dt>
            <span id="papperId">
              <Translate contentKey="mcqApp.attempt.papperId">Papper Id</Translate>
            </span>
          </dt>
          <dd>{attemptEntity.papperId}</dd>
          <dt>
            <span id="attemptNo">
              <Translate contentKey="mcqApp.attempt.attemptNo">Attempt No</Translate>
            </span>
          </dt>
          <dd>{attemptEntity.attemptNo}</dd>
        </dl>
        <Button tag={Link} to="/attempt" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attempt/${attemptEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ attempt }: IRootState) => ({
  attemptEntity: attempt.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AttemptDetail);
