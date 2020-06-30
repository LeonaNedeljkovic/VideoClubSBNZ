import { Authority } from 'src/app/model/authority.model';
export class UserDto {
    id: string;
    name: string;
    surname: string;
    username: string;
    email: string;
    password: string;
    enabled: boolean;
    age: number;
    gender: string;
    authorities: Set<Authority>;
  }