import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './mcq-papper.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMcqPapperDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const McqPapperDetail = (props: IMcqPapperDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { mcqPapperEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mcqPapperDetailsHeading">
          <Translate contentKey="mcqApp.mcqPapper.detail.title">McqPapper</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{mcqPapperEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="mcqApp.mcqPapper.title">Title</Translate>
            </span>
          </dt>
          <dd>{mcqPapperEntity.title}</dd>
          <dt>
            <span id="timeInMin">
              <Translate contentKey="mcqApp.mcqPapper.timeInMin">Time In Min</Translate>
            </span>
          </dt>
          <dd>{mcqPapperEntity.timeInMin}</dd>
          <dt>
            <Translate contentKey="mcqApp.mcqPapper.category">Category</Translate>
          </dt>
          <dd>{mcqPapperEntity.category ? mcqPapperEntity.category.id : ''}</dd>
          <dt>
            <Translate contentKey="mcqApp.mcqPapper.questions">Questions</Translate>
          </dt>
          <dd>
            {mcqPapperEntity.questions
              ? mcqPapperEntity.questions.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {mcqPapperEntity.questions && i === mcqPapperEntity.questions.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/mcq-papper" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mcq-papper/${mcqPapperEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ mcqPapper }: IRootState) => ({
  mcqPapperEntity: mcqPapper.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(McqPapperDetail);
