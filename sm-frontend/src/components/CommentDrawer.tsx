import React, { useEffect, useState } from 'react'
import Fade from '@mui/material/Fade';
import {
    Box,
    Typography,
    IconButton,
    Popover,
    Avatar,
    List,
    ListItem,
    ListItemAvatar,
    ListItemText,
    TextField,
    Stack,
    Divider,
} from '@mui/material';
import SendIcon from '@mui/icons-material/Send';
import type { Comments } from '../types/Comments';
import ExpandLessIcon from '@mui/icons-material/ExpandLess';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import toastService from '../services/ToastService';
import { ToastMessageType } from '../enums/ToastMessageType';
import commentService from '../services/CommentService';
import { useDispatch, useSelector } from 'react-redux';
import { setLoading } from '../redux/slices/appSlice';
import type { ReplyRequest } from '../types/ReplyRequest';
import type { RootState } from '../redux/store';
import type { RootEntity } from '../types/RootEntity';
import type { PostType } from '../types/PostType';
import { ReplyType } from '../enums/ReplyType';
import type { DeleteCommentRequest } from '../types/DeleteCommentRequest';
import { listenChannel, sendMessage } from '../config/webSocket';

interface CommentProps {
    comments: Comments[];
    anchor: HTMLElement | null;
    setAnchor: (value: HTMLElement | null) => void;
    setComments: (value: Comments[]) => void;
    post: PostType,
    isCommentLoading: boolean;
    expandedIds: string[];
    setExpandedIds: React.Dispatch<React.SetStateAction<string[]>>;

}

function CommentDrawer({ comments, anchor, setAnchor, setComments, post, isCommentLoading, expandedIds, setExpandedIds }: CommentProps) {


    const [activeReplyId, setActiveReplyId] = useState<string>('');
    const [replyChildText, setReplyChildText] = useState<string>('');
    const [replyParentText, setReplyParentText] = useState<string>('');


    const { currentUser } = useSelector((state: RootState) => state.app);
    const dispatch = useDispatch();


    const handleCloseComments = () => {
        setAnchor(null);
    }



    const toggleReplies = (commentId: string, isFilter: boolean) => {
        setExpandedIds((prev: string[]) => {
            if (prev.includes(commentId) && isFilter) {
                return prev.filter((x: string) => x !== commentId);
            }
            else if (prev.includes(commentId) && !isFilter) {
                return prev;
            }
            else {
                return [...prev, commentId];
            }
        });
    };

    const handleActiveReplyId = (commentId: string) => {
        setActiveReplyId(commentId);
    }



    const findCommentsByPostId = async (postId: string) => {
        try {
            const response: RootEntity<Comments[]> = await commentService.findCommentsByPostId(postId);
            if (response.status) {
                setComments(response.data);
            }

        } catch (error: any) {
            toastService.showMessage("Yorumlar getirilirken hata oluştu : " + error.response.data.exception.message, ToastMessageType.ERROR);
        }
    }

    // useEffect(() => {

    //     const setupWebSocketConnect = async () => {
    //         if (commentClientWebSocket) {
    //             listenChannel(commentClientWebSocket, "/topic/comments", (messages) => {
    //                 const receivedData: Comments[] = JSON.parse(messages);
    //                 setComments(receivedData);
    //             });
    //         }
    //     }
    //     setupWebSocketConnect();
    // }, [commentClientWebSocket, currentUser.id, post.id]);


    // const sendCommentWithWS = (request: ReplyRequest) => {
    //     if (commentClientWebSocket) {
    //         sendMessage(commentClientWebSocket, "/save-comment-with-ws", request);
    //     }
    // }



    // const createComment = async (request: ReplyRequest, replyType: ReplyType) => {
    //     try {
    //         dispatch(setLoading(true));
    //         sendCommentWithWS(request);

    //         if (replyType == ReplyType.CHILD) {
    //             setReplyChildText('');
    //             // toggleReplies(response.data.parentComment.id, false);
    //         } else {
    //             setReplyParentText('');
    //         }
    //         setActiveReplyId('');
    //     } catch (error: any) {
    //         toastService.showMessage(
    //             "Cevap oluşturulurken hata oluştu : " + error.response.data.exception.message,
    //             ToastMessageType.ERROR
    //         );
    //     } finally {
    //         dispatch(setLoading(false));
    //     }
    // };


    // const deleteCommentWithWS = (request: DeleteCommentRequest): void => {
    //     if (commentClientWebSocket) {
    //         sendMessage(commentClientWebSocket, "/delete-comment-with-ws", request);
    //     }
    // }


    // const deleteCommentById = async (commentId: string) => {
    //     dispatch(setLoading(true));
    //     try {

    //         const request: DeleteCommentRequest = { commentId: commentId, postId: post.id };
    //         deleteCommentWithWS(request);

    //         // const response: RootEntity<boolean> = await commentService.deleteCommentById(commentId);
    //         // if (response.status && response.data) {
    //         //     findCommentsByPostId(post.id);
    //         //     toastService.showMessage("Yorum silinmiştir", ToastMessageType.INFO);
    //         // }
    //     } catch (error: any) {
    //         toastService.showMessage("Yorum silinirken hata oluştu : " + error.response.data.exception.message, ToastMessageType.ERROR);
    //     } finally {
    //         dispatch(setLoading(false));
    //     }
    // }


    const buildTreeComments = (commentList: Comments[], depth = 0) => {
        return (
            <List dense sx={{ width: '100%' }}>
                {commentList.map((comment) => {
                    const hasReplies = comment.replies && comment.replies.length > 0;
                    const isExpanded = expandedIds.includes(comment.id);

                    const showReplyInput = activeReplyId === comment.id;

                    return (
                        <React.Fragment key={comment.id}>
                            <ListItem
                                alignItems="flex-start"
                                sx={{
                                    pl: depth * 2,
                                    width: '100%',
                                    display: 'flex',
                                    flexDirection: 'column',
                                    alignContent: 'flex-start',
                                    justifyContent: 'flex-start'
                                }}
                            >

                                <div style={{ width: '100%', display: 'flex', flexDirection: 'row', alignContent: 'flex-start', justifyContent: 'flex-start' }}>
                                    <ListItemAvatar>
                                        <Avatar
                                            sx={{ width: 28, height: 28 }}
                                            src={comment?.userDef?.profilePhoto}
                                        />
                                    </ListItemAvatar>

                                    <ListItemText
                                        sx={{ ml: -1 }}
                                        primary={
                                            <Stack sx={{ width: '100%', display: 'flex', flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between' }} spacing={0.5}>

                                                <Stack display="flex" flexDirection="row" alignItems={"center"} justifyContent={'center'}>
                                                    <Typography sx={{ fontSize: '13px', fontWeight: 'bold' }}>
                                                        {comment?.userDef?.username}
                                                    </Typography>
                                                    {hasReplies && (

                                                        <IconButton
                                                            size="small"
                                                            sx={{ p: 0, ml: 'auto' }}
                                                            onClick={(e) => {
                                                                e.stopPropagation();
                                                                toggleReplies(comment.id, true);
                                                            }}
                                                        >
                                                            {isExpanded ? (
                                                                <>  <ExpandLessIcon fontSize="small" />
                                                                    <Typography variant='body2' sx={{ fontSize: '12px', width: '100%', textAlign: 'left' }}>Yanıtları gizle</Typography>
                                                                </>

                                                            ) : (
                                                                <> <ExpandMoreIcon fontSize="small" />
                                                                    <Typography variant='body2' sx={{ fontSize: '12px', width: '100%', textAlign: 'left' }}>Yanıtları gör</Typography>
                                                                </>
                                                            )}
                                                        </IconButton>


                                                    )}
                                                </Stack>
                                                {/* <Stack>
                                                    {
                                                        currentUser.id === comment.userDef.id && (
                                                            <Typography variant='body2'
                                                                onClick={() => deleteCommentById(comment.id)}
                                                                sx={{
                                                                    fontSize: '11px',
                                                                    width: '100%',
                                                                    marginLeft: '10px',
                                                                    cursor: 'pointer',
                                                                    color: 'red'

                                                                }}>Sil</Typography>
                                                        )
                                                    }
                                                </Stack> */}
                                            </Stack>
                                        }
                                        secondary={comment.content}
                                        secondaryTypographyProps={{ fontSize: '12.5px' }}
                                    />
                                </div>
                                <Stack sx={{ display: 'flex', flexDirection: 'row', alignItems: 'flex-start', justifyItems: 'flex-start' }}>
                                    <Typography onClick={() => handleActiveReplyId(comment.id)} variant='body2'
                                        sx={{
                                            fontSize: '11.5px',
                                            width: '100%',
                                            textAlign: 'left',
                                            marginRight: '15px',
                                            cursor: 'pointer'
                                        }}>
                                        Yanıtla
                                    </Typography>

                                    <Typography onClick={() => handleActiveReplyId('')} variant='body2'
                                        sx={{
                                            display: showReplyInput ? 'block' : 'none',
                                            fontSize: '11.5px',
                                            width: '100%',
                                            textAlign: 'left',
                                            cursor: 'pointer'
                                        }}>
                                        Gizle
                                    </Typography>
                                </Stack>
                                {
                                    showReplyInput && (
                                        <Stack sx={{ width: '100%', display: 'flex', flexDirection: 'row', alignItems: 'center', justifyContent: 'center', marginTop: '6px' }}>
                                            <TextField
                                                value={replyChildText}
                                                onChange={(e: any) => setReplyChildText(e.target.value)}
                                                InputProps={{
                                                    sx: {
                                                        fontSize: '11.5px'
                                                    }
                                                }}
                                                fullWidth
                                                size="small"
                                                placeholder="Yorum yaz..."
                                                variant="standard"
                                            />
                                            {/* <IconButton onClick={() => createComment({
                                                content: replyChildText,
                                                userDefId: currentUser.id,
                                                postId: post.id,
                                                parentCommentId: comment.id
                                            }, ReplyType.CHILD)} color="primary" style={{ marginLeft: '5px' }}>
                                                <SendIcon style={{ fontSize: '18px' }} />
                                            </IconButton> */}
                                        </Stack>
                                    )
                                }


                            </ListItem>


                            {
                                hasReplies && isExpanded && (
                                    <Fade in={isExpanded}>
                                        <Box sx={{ pl: depth * 4 }}>
                                            {buildTreeComments(comment.replies!, depth + 1)}
                                        </Box>
                                    </Fade>
                                )
                            }

                        </React.Fragment>
                    );
                })
                }
            </List >
        );
    };


    return (
        <Popover
            open={Boolean(anchor)}
            onClose={handleCloseComments}
            aria-modal="false"
            anchorEl={anchor}
            anchorReference="anchorEl"
            anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'center',
            }}
            transformOrigin={{
                vertical: 'bottom',
                horizontal: 'center',
            }}
            PaperProps={{
                sx: {
                    width: 320,
                    maxWidth: '90%',
                    borderRadius: 1,
                    height: '300px',
                    p: 2,
                    overflowY: 'auto',
                    boxShadow: '1px 1px 2px lightgrey',
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'flex-start',
                    justifyContent: 'space-between'
                }
            }}
            TransitionComponent={Fade}
        >

            {
                isCommentLoading ? (
                    <Typography variant='body2' sx={{ fontSize: '12px', width: '100%', textAlign: 'center' }}>Yükleniyor</Typography>
                ) : comments && comments.length > 0 ? (
                    buildTreeComments(comments)
                ) : (
                    <Typography variant='body2' sx={{ fontSize: '12px', width: '100%', textAlign: 'center' }}>Yorum bulunamadı</Typography>
                )
            }

            <Box
                sx={{
                    flex: 1,
                    overflowY: 'auto',
                    p: 1,
                    width: '100%'
                }}
            >
            </Box>

            <Stack sx={{ width: '100%' }}>
                <Box
                    sx={{
                        display: 'flex',
                        gap: 1,
                        marginTop: '10px'
                    }}
                >
                    <Stack sx={{ width: '100%', display: 'flex', flexDirection: 'row', alignItems: 'center', justifyContent: 'center' }}>
                        <TextField
                            value={replyParentText}
                            onChange={(e: any) => setReplyParentText(e.target.value)}

                            InputProps={{
                                sx: {
                                    fontSize: '11.5px'
                                }
                            }}
                            fullWidth
                            size="small"
                            placeholder="Yorum yaz..."
                            variant="standard"
                        />
                        {/* <IconButton onClick={() => createComment({
                            content: replyParentText,
                            userDefId: currentUser.id,
                            postId: post.id,
                            parentCommentId: null
                        }, ReplyType.PARENT)}
                            color="primary"
                            style={{ marginLeft: '5px' }}>
                            <SendIcon style={{ fontSize: '18px' }} />
                        </IconButton> */}
                    </Stack>
                </Box>
            </Stack>

        </Popover >
    )
}

export default CommentDrawer