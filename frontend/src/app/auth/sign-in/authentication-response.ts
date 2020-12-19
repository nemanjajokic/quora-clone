export interface AuthenticationResponse {
    jwtToken: string;
    username: string;
    refreshToken: string;
    expiration: Date;
}