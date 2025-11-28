import React, { useState } from 'react'
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import Avatar from '@mui/material/Avatar';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import { Stack } from '@mui/material';
import '../css/Navbar.css';
import { FaSpider } from "react-icons/fa6";
import storageService from '../services/StorageService';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setIsAuthenticate, setCurrentUser } from '../redux/slices/appSlice';
import toastService from '../services/ToastService';
import { ToastMessageType } from '../enums/ToastMessageType';
import type { RootState } from '../redux/store';
import type { UserDef } from '../types/UserDef';
import { setOpenPostDialog } from '../redux/slices/postSlice';
import PostCreate from './PostCreate';



function Navbar() {

    const [anchorElUser, setAnchorElUser] = React.useState<null | HTMLElement>(null);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const { currentUser } = useSelector((state: RootState) => state.app);

    const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorElUser(event.currentTarget);
    };

    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };

    const openPostDialog = () => {
        dispatch(setOpenPostDialog(true));
    }

    const logout = () => {
        storageService.clearAllTokenParameters();
        dispatch(setIsAuthenticate(false));
        dispatch(setCurrentUser({} as UserDef));
        navigate("/login");
        toastService.showMessage("Çıkış yapılmıştır", ToastMessageType.INFO);

    }

    return (
        <AppBar position="static" sx={{ background: 'linear-gradient(135deg, #6a11cb 0%, #2575fc 100%)' }}>
            <Toolbar sx={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between' }}>
                <Stack>
                    <Stack onClick={() => navigate("/")} sx={{ cursor: 'pointer' }}><FaSpider style={{ fontSize: '26px' }} /></Stack>
                </Stack>

                <Stack>
                    <Box sx={{ flexGrow: 0 }}>
                        {
                            currentUser?.profilePhoto ? (
                                <>
                                    <Tooltip title="Kullanıcı ayarları">
                                        <IconButton onClick={handleOpenUserMenu}>
                                            <Avatar alt={currentUser.username} src={currentUser.profilePhoto && `${currentUser.profilePhoto}`} style={{ width: '41px', height: '41px' }} />
                                            <span style={{ marginLeft: '10px', fontSize: '13px', color: '#fff' }}>{currentUser?.firstName} {currentUser?.lastName}</span>
                                        </IconButton>
                                    </Tooltip>
                                </>
                            ) :
                                <></>
                        }
                        <Menu
                            sx={{ mt: '45px', padding: '0px' }}
                            id="menu-appbar"
                            anchorEl={anchorElUser}
                            anchorOrigin={{
                                vertical: 'top',
                                horizontal: 'right',
                            }}
                            keepMounted
                            transformOrigin={{
                                vertical: 'top',
                                horizontal: 'right',
                            }}
                            open={Boolean(anchorElUser)}
                            onClose={handleCloseUserMenu}
                        >
                            <MenuItem onClick={openPostDialog}>
                                <Typography sx={{ textAlign: 'center', fontSize: '12px' }}>Gönderi Oluştur</Typography>
                            </MenuItem>
                            <PostCreate handleCloseUserMenu={handleCloseUserMenu} />

                            <MenuItem onClick={logout}>
                                <Typography sx={{ textAlign: 'center', fontSize: '12px' }}>Çıkış Yap</Typography>
                            </MenuItem>

                        </Menu>
                    </Box>
                </Stack>
            </Toolbar>
        </AppBar >
    );
}

export default Navbar