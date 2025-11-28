import React, { useEffect, useRef, useState } from 'react'
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import { FaUser } from "react-icons/fa";
import { InputAdornment, Stack, Avatar, Typography, Button } from '@mui/material';
import { IoIosPerson } from "react-icons/io";
import { RiLockPasswordFill } from "react-icons/ri";
import DefaultAvatar from '../images/default-avatar.png';
import StringUtils from '../utils/StringUtils';
import toastService from '../services/ToastService';
import { ToastMessageType } from '../enums/ToastMessageType';
import { useDispatch } from 'react-redux';
import { setLoading } from '../redux/slices/appSlice';
import authService from '../services/AuthService';
import type { RootEntity } from '../types/RootEntity';
import type { UserDef } from '../types/UserDef';
import { useNavigate } from 'react-router-dom';


interface RegisterProps {
    open: boolean;
    onClose: (value: boolean) => void;
}

interface RegisterFormData {
    firstName: string;
    lastName: string;
    username: string;
    password: string;
    profilePhoto: File | null;
}

function Register(props: RegisterProps) {
    const { open, onClose } = props;
    const [preview, setPreview] = useState<string>('');
    const fileInputRef = useRef<HTMLInputElement>(null);

    const dispatch = useDispatch();
    const navigate = useNavigate();



    const [formData, setFormData] = useState<RegisterFormData>({
        firstName: "",
        lastName: "",
        username: "",
        password: "",
        profilePhoto: null
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    }

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (file) {
            setFormData({ ...formData, profilePhoto: file });
            setPreview(URL.createObjectURL(file));
        }
    }

    const handleAvatarClick = () => {
        fileInputRef.current?.click();
    }

    const checkInputs = (): boolean => {
        if (StringUtils.isNullOrEmpty(formData.firstName)
            || StringUtils.isNullOrEmpty(formData.lastName)
            || StringUtils.isNullOrEmpty(formData.username)
            || StringUtils.isNullOrEmpty(formData.password)
            || formData.profilePhoto == null) {
            return false;
        }
        return true;
    }

    const prepareRequest = (): FormData => {
        const formDataRequest = new FormData();
        formDataRequest.append(
            "user",
            new Blob([JSON.stringify({
                firstName: formData.firstName,
                lastName: formData.lastName,
                username: formData.username,
                password: formData.password
            })], { type: "application/json" })
        );

        if (formData.profilePhoto) {
            formDataRequest.append("profilePhoto", formData.profilePhoto);
        }
        return formDataRequest;
    }


    const clearInputs = () => {
        setFormData({
            firstName: '',
            lastName: '',
            username: '',
            password: '',
            profilePhoto: null
        })
        setPreview('');
    }

    const register = async () => {
        if (!checkInputs()) {
            toastService.showMessage("Tüm alanları doldurunuz", ToastMessageType.WARN);
            return;
        }
        try {
            dispatch(setLoading(true));
            const request: FormData = prepareRequest();
            const response: RootEntity<UserDef> = await authService.register(request);
            if (response.status) {
                toastService.showMessage("Kayıt başarılı", ToastMessageType.INFO);
                clearInputs();
                onClose(false);
                navigate("/login");

            }

        } catch (error: any) {
            toastService.showMessage(error.response.data.exception.message, ToastMessageType.ERROR);
        }
        finally {
            dispatch(setLoading(false));
        }
    }



    return (
        <React.Fragment>
            <Dialog
                open={open}
                onClose={() => onClose(false)}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    Kayıt Ol
                </DialogTitle>
                <DialogContent>
                    <DialogContentText component="div" id="alert-dialog-description" sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center' }}>
                        <TextField id="firstNameInput"
                            name='firstName'
                            onChange={handleChange}
                            sx={{ width: '300px', marginBottom: '25px' }}
                            value={formData.firstName}
                            InputProps={{
                                startAdornment: (
                                    <InputAdornment position='start'>
                                        <IoIosPerson style={{ fontSize: '18px' }} />
                                    </InputAdornment>
                                ),
                                sx: {
                                    fontSize: '13px'
                                }
                            }}
                            label="İsim" variant="standard" />

                        <TextField id="lastNameInput"
                            name='lastName'
                            onChange={handleChange}
                            sx={{ width: '300px' }}
                            value={formData.lastName}
                            InputProps={{
                                startAdornment: (
                                    <InputAdornment position='start'>
                                        <IoIosPerson style={{ fontSize: '18px' }} />
                                    </InputAdornment>
                                ),
                                sx: {
                                    fontSize: '13px'
                                }
                            }}
                            label="Soyisim" variant="standard" />

                        <TextField id="usernameInput"
                            name='username'
                            onChange={handleChange}
                            sx={{ width: '300px', marginTop: '25px' }}
                            value={formData.username}
                            InputProps={{
                                startAdornment: (
                                    <InputAdornment position='start'>
                                        <FaUser style={{ fontSize: '15px' }} />
                                    </InputAdornment>
                                ),
                                sx: {
                                    fontSize: '13px'
                                }
                            }}
                            label="Kullanıcı Adı" variant="standard" />


                        <TextField id="passwordInput"
                            name='password'
                            type='password'
                            onChange={handleChange}
                            sx={{ width: '300px', marginTop: '25px' }}
                            value={formData.password}
                            InputProps={{
                                startAdornment: (
                                    <InputAdornment position='start'>
                                        <RiLockPasswordFill style={{ fontSize: '15px' }} />
                                    </InputAdornment>
                                ),
                                sx: {
                                    fontSize: '13px'
                                }
                            }}
                            label="Şifre" variant="standard" />

                        <Stack sx={{ width: '300px', display: 'flex', flexDirection: 'row', alignItems: 'center', justifyContent: 'flex-start', marginTop: '20px' }}>
                            <Stack direction="column">
                                <Typography variant="body2" color="text.secondary" sx={{ fontSize: '11px', marginBottom: '6px' }}>
                                    Profil Fotoğrafı
                                </Typography>
                                <Avatar
                                    src={preview || DefaultAvatar}
                                    sx={{ width: 45, height: 45, cursor: 'pointer' }}
                                    onClick={handleAvatarClick}
                                >
                                </Avatar>
                                <input
                                    ref={fileInputRef}
                                    type="file"
                                    accept="image/*"
                                    hidden
                                    onChange={handleFileChange}
                                />
                            </Stack>
                        </Stack>
                    </DialogContentText>
                </DialogContent>
                <DialogActions sx={{ margin: '10px 10px' }}>
                    <Button onClick={register} size='small' color='success' variant="contained" sx={{ textTransform: 'none', marginTop: '12px', marginRight: '5px' }}
                    >Kaydol</Button>
                    <Button onClick={clearInputs} size='small' color='inherit' variant="contained" sx={{ textTransform: 'none', marginTop: '12px', marginRight: '5px' }}
                    >Temizle</Button>
                </DialogActions>
            </Dialog>
        </React.Fragment>
    )
}

export default Register