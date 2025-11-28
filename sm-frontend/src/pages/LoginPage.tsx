import React, { useState } from 'react'
import '../css/LoginPage.css';
import TextField from '@mui/material/TextField';
import { Button, InputAdornment } from '@mui/material';
import { FaUser } from "react-icons/fa";
import { Si1Password } from "react-icons/si";
import stringUtils from '../utils/StringUtils';
import toastService from '../services/ToastService';
import { ToastMessageType } from '../enums/ToastMessageType';
import authService from '../services/AuthService';
import type { AuthRequest } from '../types/AuthRequest';
import type { RootEntity } from '../types/RootEntity';
import type { JWT } from '../types/JWT';
import storageService from '../services/StorageService';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { setCurrentUser, setIsAuthenticate, setLoading } from '../redux/slices/appSlice';
import Register from '../components/Register';
import userService from '../services/UserService';
import type { UserDef } from '../types/UserDef';


function LoginPage() {

    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const [openRegisterDialog, setOpenRegisterDialog] = useState<boolean>(false);

    const navigate = useNavigate();
    const dispatch = useDispatch();


    const findByUsername = async (username: string) => {
        try {
            const response: RootEntity<UserDef> = await userService.findByUsername(username);
            if (response.status) {
                dispatch(setCurrentUser(response.data));
            }
        } catch (error: any) {
            toastService.showMessage(error.response.data.exception.message, ToastMessageType.ERROR);
        }
    }



    const authenticate = async () => {
        if (stringUtils.isNullOrEmpty(username) || stringUtils.isNullOrEmpty(password)) {
            toastService.showMessage("Bütün alanları doldurunuz.", ToastMessageType.WARN);
            return;
        }
        try {
            dispatch(setLoading(true));
            const request: AuthRequest = { username: username, password: password };
            const response: RootEntity<JWT> = await authService.authenticate(request);
            if (response.status) {
                storageService.writeAllTokenParameters(response.data);
                toastService.showMessage("Başarıyla giriş yapılmıştır", ToastMessageType.INFO);
                findByUsername(username);
                dispatch(setIsAuthenticate(true));
                navigate("/");

            } else {
                toastService.showMessage(response.errorMessage, ToastMessageType.WARN);
            }
        } catch (error: any) {
            toastService.showMessage(error.response.data.exception.message, ToastMessageType.ERROR);
        } finally {
            dispatch(setLoading(false));
        }


    }

    return (
        <div className='loginPage'>
            <div style={{ width: '400px', padding: '40px 10px', boxShadow: '1px 2px 3px grey', display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center' }}>

                <TextField id="usernameInput"
                    onChange={(e) => setUsername(e.target.value)}
                    sx={{ width: '300px' }}
                    value={username}
                    InputProps={{
                        startAdornment: (
                            <InputAdornment position='start'>
                                <FaUser />
                            </InputAdornment>
                        ),
                        sx: {
                            fontSize: '13px'
                        }
                    }}
                    label="Kullanıcı adı" variant="standard" />

                <TextField id="paswordInput"
                    type='password'
                    onChange={(e) => setPassword(e.target.value)}
                    sx={{ width: '300px', marginTop: '25px' }}
                    value={password}
                    InputProps={{
                        startAdornment: (
                            <InputAdornment position='start'>
                                <Si1Password />
                            </InputAdornment>
                        ),
                        sx: {
                            fontSize: '13px'
                        }
                    }}
                    label="Şifrex8" variant="standard" />

                <div className='buttonWrapper'>
                    <Button size='small' color='info' variant="contained" sx={{ textTransform: 'none', marginTop: '12px', marginRight: '5px' }}
                        onClick={authenticate}>Giriş Yap</Button>
                    <Button size='small' color='success' variant="contained" sx={{ textTransform: 'none', marginTop: '12px' }}
                        onClick={() => setOpenRegisterDialog(true)}
                    >Kaydol</Button>
                </div>
            </div>
            <Register open={openRegisterDialog} onClose={setOpenRegisterDialog} />

        </div>
    )
}

export default LoginPage