import { IMcqPapper } from 'app/shared/model/mcq-papper.model';

export interface ICategory {
  id?: string;
  code?: string | null;
  mcqPapper?: IMcqPapper | null;
}

export const defaultValue: Readonly<ICategory> = {};
