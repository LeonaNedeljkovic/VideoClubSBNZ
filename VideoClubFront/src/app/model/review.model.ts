import { Film } from './film.model';
import { RegistedUser } from './registered-user.model';
import { TimeInterval } from './time-interval.model';

export class Review {
    [x: string]: any;
    id: string;
    film: Film;
    user: RegistedUser;
    timeIntervals: Array<TimeInterval>;
    watched: boolean;
    watchedTime: string;
    rate: number;
    
}