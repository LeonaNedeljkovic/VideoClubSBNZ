import { Action } from './action.model';

export class ActionEvent{
    id: string;
    startDate: string;
    endDate: string;
    name: string;
    actions: Array<Action>;
}