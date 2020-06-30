import { ActionDto } from './action.dto';

export class ActionEventDto{
    id: string;
    startDate: string;
    endDate: string;
    name: string;
    actions: Array<ActionDto>;
}