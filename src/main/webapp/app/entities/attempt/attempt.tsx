import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './attempt.reducer';
import { IAttempt } from 'app/shared/model/attempt.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAttemptProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Attempt = (props: IAttemptProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { attemptList, match, loading } = props;
  return (
    <div>
      <h2 id="attempt-heading" data-cy="AttemptHeading">
        <Translate contentKey="mcqApp.attempt.home.title">Attempts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="mcqApp.attempt.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="mcqApp.attempt.home.createLabel">Create new Attempt</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {attemptList && attemptList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="mcqApp.attempt.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="mcqApp.attempt.studendId">Studend Id</Translate>
                </th>
                <th>
                  <Translate contentKey="mcqApp.attempt.papperId">Papper Id</Translate>
                </th>
                <th>
                  <Translate contentKey="mcqApp.attempt.attemptNo">Attempt No</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {attemptList.map((attempt, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${attempt.id}`} color="link" size="sm">
                      {attempt.id}
                    </Button>
                  </td>
                  <td>{attempt.studendId}</td>
                  <td>{attempt.papperId}</td>
                  <td>{attempt.attemptNo}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${attempt.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${attempt.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${attempt.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="mcqApp.attempt.home.notFound">No Attempts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ attempt }: IRootState) => ({
  attemptList: attempt.entities,
  loading: attempt.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Attempt);
