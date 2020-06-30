import { Action } from './action.model';

export class ActionEvent{
    id: number;
    startDate: string;
    endDate: string;
    name: string;
    actions: Array<Action>;
}