import axios, { type AxiosResponse } from 'axios';
import type { RefreshTokenRequest } from '../types/RefreshTokenRequest';
import authService from '../services/AuthService';
import type { RootEntity } from '../types/RootEntity';
import type { JWT } from '../types/JWT';
import storageService from '../services/StorageService';
import { ACCESS_TOKEN, REFRESH_TOKEN } from '../constants/GlobalConstants';
import { store } from '../redux/store';
import { setIsAuthenticate } from '../redux/slices/appSlice';



const axiosInstance = axios.create({
    baseURL: "http://api.enesbayram.com:30080",
});


const logout = () => {
    storageService.clearAllTokenParameters();
    store.dispatch(setIsAuthenticate(false));
}

axiosInstance.interceptors.request.use((config) => {
    const accessToken = storageService.get(ACCESS_TOKEN);
    if (accessToken) {
        config.headers["Authorization"] = `Bearer ${accessToken}`;
    }
    return config;
},
    (error) => {
        return Promise.reject(error);
    }
);


axiosInstance.interceptors.response.use(
    (res: AxiosResponse<any, any>) => {
        return res;
    },
    async (error: any) => {
        const originalConfig = error.config;
        if (error.response.status == 401 && !originalConfig._retry) {
            originalConfig._retry = true;

            try {
                const request: RefreshTokenRequest = { refreshToken: storageService.get(REFRESH_TOKEN) };
                const response: RootEntity<JWT> = await authService.refreshToken(request);
                storageService.writeAllTokenParameters(response.data);

                originalConfig.headers.Authorization = `Bearer ${storageService.get(ACCESS_TOKEN)}`;
                return axiosInstance.request(originalConfig);
            } catch (error) {
                logout();
            }
        }
        return Promise.reject(error);
    }
);

export default axiosInstance;