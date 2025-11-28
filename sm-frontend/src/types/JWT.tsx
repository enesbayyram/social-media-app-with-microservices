export interface JWT {
    accessToken: string,
    accessTokenExpired: Date,
    refreshToken: string,
    refreshTokenExpired: Date
}