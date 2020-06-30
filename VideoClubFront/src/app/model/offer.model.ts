import { Discount } from './discount.model';

export class Offer {
    id: string;
    minutes: number;
    price: number;
    discounts: Array<Discount>;
}