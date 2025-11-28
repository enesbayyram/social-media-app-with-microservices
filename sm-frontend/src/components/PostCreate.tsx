import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import { useDispatch, useSelector } from 'react-redux'
import type { RootState } from '../redux/store'
import { setOpenPostDialog } from '../redux/slices/postSlice';
import { FiPlusCircle } from "react-icons/fi";
import Skeleton from '@mui/material/Skeleton';
import { Stack } from '@mui/material';
import Textarea from '@mui/joy/Textarea';
import type { PostRequest } from '../types/PostRequest';
import postService from '../services/PostService';
import type { RootEntity } from '../types/RootEntity';
import type { PostType } from '../types/PostType';
import toastService from '../services/ToastService';
import { ToastMessageType } from '../enums/ToastMessageType';
import { useNavigate } from 'react-router-dom';



interface PostCreateProps {
    handleCloseUserMenu: () => void;
}

function PostCreate(props: PostCreateProps) {
    const { handleCloseUserMenu } = props;

    const inputPostRef = React.useRef<HTMLInputElement>(null);
    const [preview, setPreview] = React.useState<string>('');
    const [formData, setFormData] = React.useState<PostRequest>({
        postFile: null,
        content: '',
        userDefId: ''
    });

    const { openPostDialog } = useSelector((state: RootState) => state.post);
    const { currentUser } = useSelector((state: RootState) => state.app);

    const dispatch = useDispatch();
    const navigate = useNavigate();


    const handlePostClick = () => {
        inputPostRef.current?.click();
    }

    const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        })
    }

    const handlePostFile = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (file) {
            setPreview(URL.createObjectURL(file));
            setFormData({
                ...formData,
                postFile: file
            })
        }
    }

    const handlePostDialogClose = () => {
        dispatch(setOpenPostDialog(false));
    }

    const clearInputs = () => {
        setPreview('');
        setFormData({
            postFile: null,
            content: '',
            userDefId: ''
        });
        if (inputPostRef.current) {
            inputPostRef.current.value = '';
        }

    }

    const prepareRequest = (): FormData => {
        const formDataRequest = new FormData();
        if (formData.postFile) {
            formDataRequest.append("postFile", formData.postFile);
        }
        formDataRequest.append("content", formData.content);
        formDataRequest.append("userDefId", currentUser?.id);

        return formDataRequest;
    }

    const checkFields = () => {
        if (formData.postFile == null) {
            return false;
        }
        return true;
    }

    const createPost = async () => {
        try {
            if (!checkFields()) {
                toastService.showMessage("Gönderi seçilmek zorundadır.", ToastMessageType.WARN);
                return;
            }
            const response: RootEntity<PostType> = await postService.createPost(prepareRequest());
            if (response.status) {
                clearInputs();
                dispatch(setOpenPostDialog(false));
                handleCloseUserMenu();
                toastService.showMessage("Gönderi paylaşılmıştır.", ToastMessageType.INFO);
            }
        } catch (error: any) {
            toastService.showMessage("Gönderi paylaşımında hata oluştu : " + error.response.data.exception.message, ToastMessageType.ERROR);
        }
    }
    return (
        <div>
            {openPostDialog && (
                <div>
                    <Dialog
                        open={openPostDialog}
                        onClose={handlePostDialogClose}
                        aria-labelledby="alert-dialog-title"
                        aria-describedby="alert-dialog-description"
                    >
                        <DialogTitle id="alert-dialog-title" sx={{ fontSize: '15px' }}>
                            Gönderi Oluştur
                        </DialogTitle>
                        <DialogContent>
                            <Stack>
                                {
                                    preview ? (
                                        <img style={{ marginLeft: '-5px' }} src={preview} width={330} height={330} />
                                    ) :
                                        (<Skeleton variant="rectangular" sx={{ marginLeft: '-5px' }} width={330} height={330} />)
                                }
                            </Stack>
                            <Stack mt={1} ><Button onClick={handlePostClick}><FiPlusCircle style={{ fontSize: '19px', color: 'green' }} /></Button></Stack>

                            <Stack>
                                <input
                                    ref={inputPostRef}
                                    type="file"
                                    accept="image/*"
                                    hidden
                                    onChange={handlePostFile}
                                />
                            </Stack>
                            <Stack>
                                <Textarea
                                    name='content'
                                    value={formData.content}
                                    onChange={handleChange}
                                    placeholder="Bir şeyler yaz"
                                    minRows={3}
                                    sx={{
                                        '&::before': {
                                            display: 'none',
                                        },
                                        fontSize: '13px',
                                        width: '330px',
                                        marginLeft: '-5px',
                                        marginTop: '12px'
                                    }}
                                />
                            </Stack>

                            <Stack mt={1.5} sx={{ display: 'flex', flexDirection: 'row', alignItems: 'center', justifyContent: 'flex-end' }}>
                                <Button onClick={createPost} size='small' color='success' variant="contained" sx={{ textTransform: 'none', marginRight: '10px' }}
                                >Oluştur</Button>
                                <Button onClick={clearInputs} size='small' color='info' variant="contained" sx={{ textTransform: 'none' }}
                                >Temizle</Button>
                            </Stack>

                        </DialogContent>
                    </Dialog>
                </div>
            )
            }
        </div >
    )
}

export default PostCreate