import { Authority } from './authority.model';
import {User} from './user.model';
import { Action } from './action.model';
import { Review } from './review.model';
import { Film } from './film.model';
import { Purchase } from './purchase.model';
import { Notification } from './notification.model';

export class RegistedUser extends User {
    age: number;
    ageCategory: string;
    gender: string;
    registryDate: string;
    immunityPoints: number;
    availableMinutes: number;
    title: string;
    immunity: string;
    action: Array<Action>;
    allowedToPurchase: boolean;
    reviews: Array<Review>;
    notifications: Array<Notification>;
    favouriteFilms: Array<Film>;
    purchases: Array<Purchase>;



}