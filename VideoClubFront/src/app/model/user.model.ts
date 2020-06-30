import { Authority } from './authority.model';
export class User  {
    id: string;
    username: string;
    password: string;
    email: string;
    lastPasswordResetDate: string;
    authorities: Set<Authority>;
    allowedToLogin: boolean;   
}