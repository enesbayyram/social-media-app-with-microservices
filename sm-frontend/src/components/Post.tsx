import React, { useEffect, useRef, useState } from 'react'
import type { PostType } from '../types/PostType'
import Card from '@mui/material/Card';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import CardActionArea from '@mui/material/CardActionArea';
import { Avatar, IconButton, Stack } from '@mui/material';
import { FaRegComment } from "react-icons/fa6";
import CommentDrawer from './CommentDrawer';
import { useDispatch, useSelector } from 'react-redux';
import { setLoading, setPostClientWebSocket } from '../redux/slices/appSlice';
import commentService from '../services/CommentService';
import type { RootEntity } from '../types/RootEntity';
import type { Comments } from '../types/Comments';
import toastService from '../services/ToastService';
import { ToastMessageType } from '../enums/ToastMessageType';
import postLikeService from '../services/PostLikeService';
import type { PostLikeRequest } from '../types/PostLikeRequest';
import type { RootState } from '../redux/store';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import type { PostLikeStatusResponse } from '../types/PostLikeStatusResponse';
import { FaRegHeart } from "react-icons/fa";
import { Client } from 'stompjs';
import { connectWebSocket, listenChannel, sendMessage } from '../config/webSocket';






interface PostProps {
    post: PostType
}

function Post(props: PostProps) {
    const { post } = props;
    const { currentUser, postClientWebSocket } = useSelector((state: RootState) => state.app);

    const dispatch = useDispatch();

    const [comments, setComments] = useState<Comments[]>([]);
    const [isCommentLoading, setIsCommentLoading] = useState<boolean>(false);
    const [isUserPostLike, setIsUserPostLike] = useState<boolean>(false);
    const [expandedIds, setExpandedIds] = useState<string[]>([]);
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const [postLikeCount, setPostLikeCount] = useState<number>();
    const postRef = useRef<HTMLDivElement>(null);
    const [initialLoadedDone, setInitialLoadedDone] = useState<boolean>(false);


    const formattedPostCreatedTime = (postCreateTime: Date) => {

        const date = new Date(postCreateTime);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const year = date.getFullYear();

        const formatted = `${day}.${month}.${year}`;
        return formatted;
    }

    const diff = (): string => {
        const now = new Date();
        const target = new Date(post.createTime);

        const diffMs = now.getTime() - target.getTime();
        const diffMinutes = Math.floor(diffMs / (1000 * 60));
        const diffHours = Math.floor(diffMs / (1000 * 60 * 60));
        const diffDay = Math.floor(diffMs / (1000 * 60 * 60 * 24));

        if (diffMinutes < 60) {
            return diffMinutes + "d";
        } else if (diffHours < 24) {
            return diffHours + "s";
        } else if (diffDay <= 1) {
            return diffDay + "g";
        }
        return formattedPostCreatedTime(post.createTime);

    }


    const fetchPostLikeStatus = async () => {
        try {
            const response: RootEntity<PostLikeStatusResponse> = await postLikeService.findPostLikeStatus(post?.id, currentUser?.id);
            if (response.status) {
                setIsUserPostLike(response.data.isUserPostLike);
                setPostLikeCount(response.data.postLikeCount);
            }
        } catch (error) {
            toastService.showMessage("Gönderi beğenisi çekilirken hata oluştu.", ToastMessageType.ERROR);
        }
    }

    useEffect(() => {

        if (!initialLoadedDone) {
            fetchPostLikeStatus();
            setInitialLoadedDone(true);
        }
        const setupWebSockets = async () => {

            if (postClientWebSocket) {
                listenChannel(postClientWebSocket, `/topic/post-like-post/${post?.id}`, (msg) => {
                    const data = JSON.parse(msg);
                    setPostLikeCount(data.postLikeCount);
                });

                listenChannel(postClientWebSocket, `/topic/post-like-user/${post?.id}/${currentUser?.id}`, (msg) => {
                    const data = JSON.parse(msg);
                    setIsUserPostLike(data.isUserPostLike);
                });
            }
        };

        setupWebSockets();
    }, [currentUser?.id, post?.id, postClientWebSocket]);



    const switchPostLike = async () => {
        try {
            const request: PostLikeRequest = { postId: post.id, userDefId: currentUser.id };
            const response: RootEntity<boolean> = await postLikeService.switchPostLike(request);
            if (response.data && postClientWebSocket && postClientWebSocket.connected) {
                sendMessage(postClientWebSocket, "/post-like-status-with-ws", request);
            }
        } catch (error) {
            toastService.showMessage("Gönderi beğenileri çekilirken hata oluştu.", ToastMessageType.ERROR);
        }
    }

    const showComments = async () => {
        if (postRef.current) {
            setAnchorEl(postRef.current);
        }
        try {
            dispatch(setLoading(true));
            setIsCommentLoading(true);
            const response: RootEntity<Comments[]> = await commentService.findCommentsByPostId(post.id);
            if (response.status) {
                setComments(response.data);
                setExpandedIds([]);
            }

        } catch (error: any) {
            toastService.showMessage("Yorumlar getirilirken hata oluştu : " + error.response.data.exception.message, ToastMessageType.ERROR);
        } finally {
            dispatch(setLoading(false));
            setIsCommentLoading(false);
        }
    }

    return (
        <>
            <div ref={postRef} style={{ margin: '28px 0px' }}>

                <Stack display="flex" direction="row" alignItems="center" justifyContent="flex-start" >
                    <Avatar
                        src={post.userDef.profilePhoto}
                        sx={{ width: 40, height: 40, cursor: 'pointer' }}
                    >
                    </Avatar>
                    <Typography variant="body1" sx={{ fontSize: '15px', fontWeight: 'bold', marginLeft: '7px' }}>
                        {post.userDef.username}
                    </Typography>

                    <Typography variant="body1" sx={{ fontSize: '13px', marginLeft: '7px' }}>
                        • {diff()}
                    </Typography>
                </Stack>
                <Card sx={{ width: 350, maxHeight: 600, margin: '15px 0px', boxShadow: '3px 6px 20px grey' }}>
                    <CardActionArea>
                        <CardMedia
                            component="img"
                            height="350"
                            src={post.picture}
                            alt={post.userDef.username}
                        />


                    </CardActionArea>

                </Card>

                <Stack sx={{ display: 'flex', flexDirection: 'row', alignItems: 'center', justifyContent: 'flex-start' }} mt={2.5}>
                    <div style={{ marginRight: '15px', cursor: 'pointer', display: 'flex', flexDirection: 'row', alignItems: 'center', justifyContent: 'center' }}>
                        <div style={{ width: '41px' }}>
                            <IconButton onClick={switchPostLike} style={{ cursor: 'pointer', color: 'black' }}>
                                {
                                    isUserPostLike ? (
                                        <FontAwesomeIcon
                                            style={{
                                                fontSize: '26px',
                                                marginLeft: '-4px'
                                            }}
                                            icon={faHeart} color="red" />
                                    ) :
                                        (<FaRegHeart
                                            style={{
                                                fontSize: '26px'
                                            }}
                                        />
                                        )
                                }

                            </IconButton>
                        </div>
                        <div style={{ marginBottom: '2px' }}>
                            <Typography variant="body2">
                                {postLikeCount}
                            </Typography>
                        </div>
                    </div>
                    <IconButton style={{ marginBottom: '+2px', cursor: 'pointer', color: 'black' }} onClick={showComments}><FaRegComment style={{ fontSize: '26px' }} /> </IconButton>
                </Stack>

                <Typography variant="body1" sx={{ fontSize: '15px', marginBottom: '6px', marginTop: '20px' }}>
                    <span style={{ fontWeight: 'bold' }}>{post.userDef.username}</span> -  {post.content}
                </Typography>


            </div >
            <CommentDrawer
                comments={comments}
                anchor={anchorEl}
                setAnchor={setAnchorEl}
                setComments={setComments}
                post={post}
                isCommentLoading={isCommentLoading}
                expandedIds={expandedIds}
                setExpandedIds={setExpandedIds}

            />
            <hr style={{ width: '100%', border: '0.1px solid lightgrey' }} />
        </>
    )
}

export default Post