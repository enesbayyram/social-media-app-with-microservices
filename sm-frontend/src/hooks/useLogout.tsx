import React from 'react'
import { useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom';
import storageService from '../services/StorageService';
import { setIsAuthenticate } from '../redux/slices/appSlice';

function useLogout() {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    return () => {
        storageService.clearAllTokenParameters();
        dispatch(setIsAuthenticate(false));
        navigate("/login");
    }
}

export default useLogout