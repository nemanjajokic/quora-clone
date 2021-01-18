export interface AuthenticationResponse {
    jwtToken: string;
    username: string;
    imageUri: string;
    refreshToken: string;
    expiration: Date;
}