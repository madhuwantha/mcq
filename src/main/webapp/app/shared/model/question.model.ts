import { IMcqPapper } from 'app/shared/model/mcq-papper.model';

export interface IQuestion {
  id?: string;
  code?: string | null;
  questionImageLink?: string | null;
  answerImageLiks?: string | null;
  correctOne?: number | null;
  pappers?: IMcqPapper[] | null;
}

export const defaultValue: Readonly<IQuestion> = {};
