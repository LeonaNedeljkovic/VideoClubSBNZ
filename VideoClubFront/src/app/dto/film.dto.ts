export class FilmDto {
    id: string;
    name: string;
    description: string;
    genre: string;
    year: number;
    actorIds: Array<string>;
    directorId: string;
    duration: number;
    poster: string;
    writtenId: string;
}