import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './answer.reducer';
import { IAnswer } from 'app/shared/model/answer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAnswerProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Answer = (props: IAnswerProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { answerList, match, loading } = props;
  return (
    <div>
      <h2 id="answer-heading" data-cy="AnswerHeading">
        <Translate contentKey="mcqApp.answer.home.title">Answers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="mcqApp.answer.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="mcqApp.answer.home.createLabel">Create new Answer</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {answerList && answerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="mcqApp.answer.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="mcqApp.answer.questionId">Question Id</Translate>
                </th>
                <th>
                  <Translate contentKey="mcqApp.answer.answer">Answer</Translate>
                </th>
                <th>
                  <Translate contentKey="mcqApp.answer.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="mcqApp.answer.attempt">Attempt</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {answerList.map((answer, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${answer.id}`} color="link" size="sm">
                      {answer.id}
                    </Button>
                  </td>
                  <td>{answer.questionId}</td>
                  <td>{answer.answer}</td>
                  <td>{answer.status ? 'true' : 'false'}</td>
                  <td>{answer.attempt ? <Link to={`attempt/${answer.attempt.id}`}>{answer.attempt.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${answer.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${answer.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${answer.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="mcqApp.answer.home.notFound">No Answers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ answer }: IRootState) => ({
  answerList: answer.entities,
  loading: answer.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Answer);
