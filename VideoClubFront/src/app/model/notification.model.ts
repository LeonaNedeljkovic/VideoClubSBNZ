import { RegistedUser } from './registered-user.model';

export class Notification {
    id: string;
    header: string;
    body: string;
    opened: boolean;
    user: RegistedUser;
}