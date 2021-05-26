import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAttempt, defaultValue } from 'app/shared/model/attempt.model';

export const ACTION_TYPES = {
  FETCH_ATTEMPT_LIST: 'attempt/FETCH_ATTEMPT_LIST',
  FETCH_ATTEMPT: 'attempt/FETCH_ATTEMPT',
  CREATE_ATTEMPT: 'attempt/CREATE_ATTEMPT',
  UPDATE_ATTEMPT: 'attempt/UPDATE_ATTEMPT',
  PARTIAL_UPDATE_ATTEMPT: 'attempt/PARTIAL_UPDATE_ATTEMPT',
  DELETE_ATTEMPT: 'attempt/DELETE_ATTEMPT',
  RESET: 'attempt/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAttempt>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AttemptState = Readonly<typeof initialState>;

// Reducer

export default (state: AttemptState = initialState, action): AttemptState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ATTEMPT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ATTEMPT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ATTEMPT):
    case REQUEST(ACTION_TYPES.UPDATE_ATTEMPT):
    case REQUEST(ACTION_TYPES.DELETE_ATTEMPT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ATTEMPT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ATTEMPT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ATTEMPT):
    case FAILURE(ACTION_TYPES.CREATE_ATTEMPT):
    case FAILURE(ACTION_TYPES.UPDATE_ATTEMPT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ATTEMPT):
    case FAILURE(ACTION_TYPES.DELETE_ATTEMPT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ATTEMPT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ATTEMPT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ATTEMPT):
    case SUCCESS(ACTION_TYPES.UPDATE_ATTEMPT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ATTEMPT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ATTEMPT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/attempts';

// Actions

export const getEntities: ICrudGetAllAction<IAttempt> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ATTEMPT_LIST,
  payload: axios.get<IAttempt>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAttempt> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ATTEMPT,
    payload: axios.get<IAttempt>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAttempt> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ATTEMPT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAttempt> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ATTEMPT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAttempt> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ATTEMPT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAttempt> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ATTEMPT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
