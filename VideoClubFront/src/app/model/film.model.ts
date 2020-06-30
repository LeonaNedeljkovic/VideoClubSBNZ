import { Artist } from './artist.model';
import { Review } from './review.model';

export class Film {
    id: string;
    name: string;
    description: string;
    genre: string;
    duration: number;
    year: number;
    rating: number;
    poster: string;
    actors: Array<Artist>;
    director: Artist;
    writtenBy:Artist;
    reviews: Array<Review>;
    restrictedAgeCategories: Array<String>;
}