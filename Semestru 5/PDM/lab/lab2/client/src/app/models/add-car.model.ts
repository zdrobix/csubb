export interface AddCarRequest {
    id_user: number,
    name: string;
    registration_number: string;
    accident_count: number | null;
    registration_date: Date;
}