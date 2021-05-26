import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './answer.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAnswerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AnswerDetail = (props: IAnswerDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { answerEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="answerDetailsHeading">
          <Translate contentKey="mcqApp.answer.detail.title">Answer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{answerEntity.id}</dd>
          <dt>
            <span id="questionId">
              <Translate contentKey="mcqApp.answer.questionId">Question Id</Translate>
            </span>
          </dt>
          <dd>{answerEntity.questionId}</dd>
          <dt>
            <span id="answer">
              <Translate contentKey="mcqApp.answer.answer">Answer</Translate>
            </span>
          </dt>
          <dd>{answerEntity.answer}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="mcqApp.answer.status">Status</Translate>
            </span>
          </dt>
          <dd>{answerEntity.status ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="mcqApp.answer.attempt">Attempt</Translate>
          </dt>
          <dd>{answerEntity.attempt ? answerEntity.attempt.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/answer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/answer/${answerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ answer }: IRootState) => ({
  answerEntity: answer.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AnswerDetail);
