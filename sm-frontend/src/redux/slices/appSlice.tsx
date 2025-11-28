import { createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'
import type { UserDef } from '../../types/UserDef';
import type { Client } from 'stompjs';

interface AppSlice {
    loading: boolean;
    isAuthenticate: boolean;
    currentUser: UserDef;
    postClientWebSocket: Client;
    commentClientWebSocket: Client;

}


const initialState: AppSlice = {
    loading: false,
    isAuthenticate: false,
    currentUser: {} as UserDef,
    postClientWebSocket: {} as Client,
    commentClientWebSocket: {} as Client
}



export const appSlice = createSlice({
    name: "appSlice",
    initialState,
    reducers: {
        setLoading: (state: AppSlice, action: PayloadAction<boolean>) => {
            state.loading = action.payload;
        },
        setIsAuthenticate: (state: AppSlice, action: PayloadAction<boolean>) => {
            state.isAuthenticate = action.payload;
        },
        setCurrentUser: (state: AppSlice, action: PayloadAction<UserDef>) => {
            state.currentUser = action.payload;
        },
        setPostClientWebSocket: (state: AppSlice, action: PayloadAction<Client>) => {
            state.postClientWebSocket = action.payload;
        },
        setCommentClientWebSocket: (state: AppSlice, action: PayloadAction<Client>) => {
            state.commentClientWebSocket = action.payload;
        }
    },
    extraReducers: () => {

    }
});

export const { setLoading, setIsAuthenticate, setCurrentUser, setPostClientWebSocket, setCommentClientWebSocket } = appSlice.actions
export default appSlice.reducer
