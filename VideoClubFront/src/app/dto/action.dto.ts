export class ActionDto{
    id: string;
    description: string;
    actionType: string;
    rank: string;
    amount: number;
    genres: Array<string>;
    discountOffersIds : Array<number>;
}