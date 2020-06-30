import { RegistedUser } from './registered-user.model';
import { Offer } from './offer.model';


export class Purchase {
    id: string;
    date: String;
    discount: number;
    price: number;
    purchasedMinutes: number;
    offer: Offer;
    user: RegistedUser;
}