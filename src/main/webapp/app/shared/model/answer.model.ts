import { IAttempt } from 'app/shared/model/attempt.model';

export interface IAnswer {
  id?: string;
  questionId?: string | null;
  answer?: number | null;
  status?: boolean | null;
  attempt?: IAttempt | null;
}

export const defaultValue: Readonly<IAnswer> = {
  status: false,
};
