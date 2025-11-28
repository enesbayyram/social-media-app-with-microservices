import { createSlice, type PayloadAction } from '@reduxjs/toolkit'
import type { PostType } from '../../types/PostType';

const initialState: PostSlice = {
    openPostDialog: false,
    posts: []
}

interface PostSlice {
    openPostDialog: boolean;
    posts: PostType[]
}


export const postSlice = createSlice({
    name: "postSlice",
    initialState,
    reducers: {
        setOpenPostDialog: (state: PostSlice, action: PayloadAction<boolean>) => {
            state.openPostDialog = action.payload;
        },
        setPosts: (state: PostSlice, action: PayloadAction<PostType[]>) => {
            state.posts = action.payload;
        }
    },
    extraReducers: () => {

    }
});

export const { setOpenPostDialog, setPosts } = postSlice.actions
export default postSlice.reducer
