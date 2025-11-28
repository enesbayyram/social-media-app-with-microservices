import React, { useEffect } from 'react'
import { Route, Routes, useNavigate } from 'react-router-dom'
import HomePage from '../pages/HomePage'
import LoginPage from '../pages/LoginPage'
import ProtectedRoute from './ProtectedRoute'
import { useSelector } from 'react-redux'
import type { RootState } from '../redux/store'
import storageService from '../services/StorageService'
import { ACCESS_TOKEN } from '../constants/GlobalConstants'

function RouterConfig() {

    const { isAuthenticate } = useSelector((state: RootState) => state.app);
    const navigate = useNavigate();

    useEffect(() => {
        if (!isAuthenticate && storageService.get(ACCESS_TOKEN) == null) {
            navigate("/login");
        }
    }, [isAuthenticate, navigate])


    return (
        <>
            <Routes>
                <Route path='/'
                    element={<ProtectedRoute><HomePage /></ProtectedRoute>} />
                <Route path='/login' element={<LoginPage />} />
            </Routes>

        </>

    )
}

export default RouterConfig