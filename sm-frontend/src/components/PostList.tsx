import React, { useEffect, useState } from 'react'
import postService from '../services/PostService'
import type { RootEntity } from '../types/RootEntity';
import type { PostType } from '../types/PostType';
import toastService from '../services/ToastService';
import { ToastMessageType } from '../enums/ToastMessageType';
import Post from './Post';
import { useLocation } from 'react-router-dom';
import Container from '@mui/material/Container';
import { useDispatch, useSelector } from 'react-redux';
import type { RootState } from '../redux/store';
import { setPosts } from '../redux/slices/postSlice';
import type { Client } from 'stompjs';
import { connectWebSocket, listenChannel } from '../config/webSocket';

function PostList() {

    const { posts } = useSelector((state: RootState) => state.post);
    const { isAuthenticate, currentUser, postClientWebSocket } = useSelector((state: RootState) => state.app);
    const [initialLoadDone, setInitialLoadDone] = useState<boolean>(false);

    const dispatch = useDispatch();
    const location = useLocation();

    const findAllPost = async () => {
        try {
            const response: RootEntity<PostType[]> = await postService.findAllPost();
            if (response.status) {
                dispatch(setPosts(response.data));
            }
        } catch (error: any) {
            toastService.showMessage("Gönderiler getirilirken hata oluştu : " + error.response.data.exception.message, ToastMessageType.ERROR);
        }
    }


    useEffect(() => {
        if (isAuthenticate) {
            if (!initialLoadDone) {
                findAllPost();
                setInitialLoadDone(true);
            }

            const setupWebSocket = async () => {
                if (postClientWebSocket) {
                    listenChannel(postClientWebSocket, "/topic/posts", (messages) => {
                        const receivedData: PostType[] = JSON.parse(messages);
                        dispatch(setPosts(receivedData));
                    });
                }
            };
            setupWebSocket();
        }
    }, [currentUser?.id, location, postClientWebSocket, currentUser.id]);


    return (
        <Container maxWidth="md" sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', marginTop: '20px' }}>
            {
                posts && posts.map((post: PostType, index: number) => (
                    <Post key={index} post={post} />
                ))
            }
        </Container>
    )
}

export default PostList