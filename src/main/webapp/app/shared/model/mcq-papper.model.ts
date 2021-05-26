import { ICategory } from 'app/shared/model/category.model';
import { IQuestion } from 'app/shared/model/question.model';

export interface IMcqPapper {
  id?: string;
  title?: string | null;
  timeInMin?: number | null;
  category?: ICategory | null;
  questions?: IQuestion[] | null;
}

export const defaultValue: Readonly<IMcqPapper> = {};
