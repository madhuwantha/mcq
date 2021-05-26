import { IAnswer } from 'app/shared/model/answer.model';

export interface IAttempt {
  id?: string;
  studendId?: string | null;
  papperId?: string | null;
  attemptNo?: number | null;
  answers?: IAnswer[] | null;
}

export const defaultValue: Readonly<IAttempt> = {};
