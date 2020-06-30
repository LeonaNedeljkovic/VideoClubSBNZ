import { Film } from '../model/film.model';

export class ReportDto {
    earned: number;
    date: string;
    film: Film;
    numberOfViews: number;
}
