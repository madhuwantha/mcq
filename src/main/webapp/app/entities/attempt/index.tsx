import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Attempt from './attempt';
import AttemptDetail from './attempt-detail';
import AttemptUpdate from './attempt-update';
import AttemptDeleteDialog from './attempt-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AttemptUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AttemptUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AttemptDetail} />
      <ErrorBoundaryRoute path={match.url} component={Attempt} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AttemptDeleteDialog} />
  </>
);

export default Routes;
