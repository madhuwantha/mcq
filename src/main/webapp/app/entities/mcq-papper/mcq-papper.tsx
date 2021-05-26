import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './mcq-papper.reducer';
import { IMcqPapper } from 'app/shared/model/mcq-papper.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMcqPapperProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const McqPapper = (props: IMcqPapperProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { mcqPapperList, match, loading } = props;
  return (
    <div>
      <h2 id="mcq-papper-heading" data-cy="McqPapperHeading">
        <Translate contentKey="mcqApp.mcqPapper.home.title">Mcq Pappers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="mcqApp.mcqPapper.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="mcqApp.mcqPapper.home.createLabel">Create new Mcq Papper</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {mcqPapperList && mcqPapperList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="mcqApp.mcqPapper.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="mcqApp.mcqPapper.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="mcqApp.mcqPapper.timeInMin">Time In Min</Translate>
                </th>
                <th>
                  <Translate contentKey="mcqApp.mcqPapper.category">Category</Translate>
                </th>
                <th>
                  <Translate contentKey="mcqApp.mcqPapper.questions">Questions</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {mcqPapperList.map((mcqPapper, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${mcqPapper.id}`} color="link" size="sm">
                      {mcqPapper.id}
                    </Button>
                  </td>
                  <td>{mcqPapper.title}</td>
                  <td>{mcqPapper.timeInMin}</td>
                  <td>{mcqPapper.category ? <Link to={`category/${mcqPapper.category.id}`}>{mcqPapper.category.id}</Link> : ''}</td>
                  <td>
                    {mcqPapper.questions
                      ? mcqPapper.questions.map((val, j) => (
                          <span key={j}>
                            <Link to={`question/${val.id}`}>{val.id}</Link>
                            {j === mcqPapper.questions.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${mcqPapper.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mcqPapper.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mcqPapper.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="mcqApp.mcqPapper.home.notFound">No Mcq Pappers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ mcqPapper }: IRootState) => ({
  mcqPapperList: mcqPapper.entities,
  loading: mcqPapper.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(McqPapper);
