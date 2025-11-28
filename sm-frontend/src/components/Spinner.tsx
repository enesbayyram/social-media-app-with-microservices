import React from 'react'
import Backdrop from '@mui/material/Backdrop';
import CircularProgress from '@mui/material/CircularProgress';
import { useDispatch, useSelector } from 'react-redux';
import { setLoading } from '../redux/slices/appSlice';
import type { RootState } from '../redux/store';

function Spinner() {

    const dispatch = useDispatch();
    const { loading } = useSelector((store: RootState) => store.app);

    const handleClose = () => {
        dispatch(setLoading(false));
    }

    return (
        <Backdrop
            sx={(theme) => ({ color: '#fff', zIndex: theme.zIndex.drawer + 1 })}
            open={loading}
            onClick={handleClose}
        >
            <CircularProgress color="inherit" />
        </Backdrop>
    )
}

export default Spinner