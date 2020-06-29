import { Authority } from './authority.model';

export class UserModel {
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

    constructor(){
      
    }
  }