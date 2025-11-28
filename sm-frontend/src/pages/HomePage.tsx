import React, { useEffect, useState } from 'react'
import Navbar from '../components/Navbar'
import PostList from '../components/PostList'
import type { RootState } from '../redux/store';
import { useDispatch, useSelector } from 'react-redux';
import { connectWebSocket } from '../config/webSocket';
import type { Client } from 'stompjs';
import { setPostClientWebSocket } from '../redux/slices/appSlice';

function HomePage() {

    const { currentUser } = useSelector((state: RootState) => state.app);
    const dispatch = useDispatch();


    useEffect(() => {

        const setupWebSocketConnect = async () => {
            const postClient: Client = await connectWebSocket("/sm-post-manager/ws");
            dispatch(setPostClientWebSocket(postClient));

            // const commentClient: Client = await connectWebSocket("/sm-comment-manager/ws");
            // dispatch(setCommentClientWebSocket(commentClient));
        }
        setupWebSocketConnect();
    }, [currentUser?.id])

    return (
        <div>
            <Navbar />
            <PostList />
        </div>
    )
}

export default HomePage