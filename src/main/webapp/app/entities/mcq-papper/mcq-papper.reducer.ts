import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMcqPapper, defaultValue } from 'app/shared/model/mcq-papper.model';

export const ACTION_TYPES = {
  FETCH_MCQPAPPER_LIST: 'mcqPapper/FETCH_MCQPAPPER_LIST',
  FETCH_MCQPAPPER: 'mcqPapper/FETCH_MCQPAPPER',
  CREATE_MCQPAPPER: 'mcqPapper/CREATE_MCQPAPPER',
  UPDATE_MCQPAPPER: 'mcqPapper/UPDATE_MCQPAPPER',
  PARTIAL_UPDATE_MCQPAPPER: 'mcqPapper/PARTIAL_UPDATE_MCQPAPPER',
  DELETE_MCQPAPPER: 'mcqPapper/DELETE_MCQPAPPER',
  RESET: 'mcqPapper/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMcqPapper>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type McqPapperState = Readonly<typeof initialState>;

// Reducer

export default (state: McqPapperState = initialState, action): McqPapperState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MCQPAPPER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MCQPAPPER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MCQPAPPER):
    case REQUEST(ACTION_TYPES.UPDATE_MCQPAPPER):
    case REQUEST(ACTION_TYPES.DELETE_MCQPAPPER):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_MCQPAPPER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MCQPAPPER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MCQPAPPER):
    case FAILURE(ACTION_TYPES.CREATE_MCQPAPPER):
    case FAILURE(ACTION_TYPES.UPDATE_MCQPAPPER):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_MCQPAPPER):
    case FAILURE(ACTION_TYPES.DELETE_MCQPAPPER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MCQPAPPER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MCQPAPPER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MCQPAPPER):
    case SUCCESS(ACTION_TYPES.UPDATE_MCQPAPPER):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_MCQPAPPER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MCQPAPPER):
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

const apiUrl = 'api/mcq-pappers';

// Actions

export const getEntities: ICrudGetAllAction<IMcqPapper> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MCQPAPPER_LIST,
  payload: axios.get<IMcqPapper>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IMcqPapper> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MCQPAPPER,
    payload: axios.get<IMcqPapper>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMcqPapper> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MCQPAPPER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMcqPapper> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MCQPAPPER,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IMcqPapper> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_MCQPAPPER,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMcqPapper> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MCQPAPPER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
