import { configureStore } from '@reduxjs/toolkit'
import appReducer from '../redux/slices/appSlice';
import postReducer from '../redux/slices/postSlice';

export const store = configureStore({
    reducer: {
        app: appReducer,
        post: postReducer
    },
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware({
            serializableCheck: false, // 🔥 uyarı kapanır
        }),
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch