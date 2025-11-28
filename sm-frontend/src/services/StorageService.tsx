import { ACCESS_TOKEN, ACCESS_TOKEN_EXPIRE_IN, REFRESH_TOKEN, REFRESH_TOKEN_EXPIRE_IN } from "../constants/GlobalConstants";
import type { JWT } from "../types/JWT";

class StorageService {

    write(key: string, value: string): void {
        localStorage.setItem(key, value);
    }

    get(key: string): string | null {
        return localStorage.getItem(key);
    }

    delete(key: string): void {
        localStorage.removeItem(key);
    }

    writeAllTokenParameters(jwt: JWT): void {
        this.write(ACCESS_TOKEN, jwt?.accessToken);
        this.write(ACCESS_TOKEN_EXPIRE_IN, jwt?.accessTokenExpired.toString());
        this.write(REFRESH_TOKEN, jwt?.refreshToken);
        this.write(REFRESH_TOKEN_EXPIRE_IN, jwt?.refreshTokenExpired.toString());
    }

    clearAllTokenParameters(): void {
        this.delete(ACCESS_TOKEN);
        this.delete(ACCESS_TOKEN_EXPIRE_IN);
        this.delete(REFRESH_TOKEN);
        this.delete(REFRESH_TOKEN_EXPIRE_IN);
    }
}

export default new StorageService();