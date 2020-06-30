export class FilmDto {
    id: string;
    name: string;
    description: string;
    genre: string;
    year: number;
    actorIds: Array<number>;
    dirctorId: number;
    duration: number;
}