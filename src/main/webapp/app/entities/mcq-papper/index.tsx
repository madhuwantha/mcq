import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import McqPapper from './mcq-papper';
import McqPapperDetail from './mcq-papper-detail';
import McqPapperUpdate from './mcq-papper-update';
import McqPapperDeleteDialog from './mcq-papper-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={McqPapperUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={McqPapperUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={McqPapperDetail} />
      <ErrorBoundaryRoute path={match.url} component={McqPapper} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={McqPapperDeleteDialog} />
  </>
);

export default Routes;
