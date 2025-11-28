import React from 'react'
import { useSelector } from 'react-redux'
import type { RootState } from '../redux/store'
import LoginPage from '../pages/LoginPage';

interface ProtectedRouteType {
    children: any
}

function ProtectedRoute(props: ProtectedRouteType) {

    const { isAuthenticate } = useSelector((state: RootState) => state.app);
    const { children } = props;
    return (
        <div>
            {isAuthenticate ? (children) : <LoginPage />}
        </div>
    )
}

export default ProtectedRoute