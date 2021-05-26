import { IQuestion } from 'app/shared/model/question.model';
import { ICategory } from 'app/shared/model/category.model';

export interface IMcqPapper {
  id?: string;
  title?: string | null;
  timeInMin?: number | null;
  questions?: IQuestion[] | null;
  categories?: ICategory[] | null;
}

export const defaultValue: Readonly<IMcqPapper> = {};
