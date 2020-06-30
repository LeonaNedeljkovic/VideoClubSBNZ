export class VideoContentDto {
    id: string;
    name: string;
    description: string;
    genre: string;
    year: number;
    actorIds: Array<number>;
    directorId: number;
    duration: number;
}