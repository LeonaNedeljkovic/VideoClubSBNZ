import { Film } from './film.model';

export class Artist {
    id: string;
    name: string;
    surname: string;
    roles: Array<Film>;
    directed: Array<Film>;
    written: Array<Film>;
}