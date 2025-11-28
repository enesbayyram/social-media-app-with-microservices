import './App.css'
import RouterConfig from './config/RouterConfig'
import Spinner from './components/Spinner'
import userService from './services/UserService'
import toastService from './services/ToastService'
import type { UserDef } from './types/UserDef'
import type { RootEntity } from './types/RootEntity'
import { ToastMessageType } from './enums/ToastMessageType'
import { setCurrentUser, setIsAuthenticate, setLoading } from './redux/slices/appSlice'
import { useDispatch, useSelector } from 'react-redux'
import { useEffect, useState } from 'react'
import storageService from './services/StorageService'
import { ACCESS_TOKEN } from './constants/GlobalConstants'
import { jwtDecode } from "jwt-decode";
import { useNavigate } from 'react-router-dom'
import postService from './services/PostService'
import type { PostType } from './types/PostType'
import { setPosts } from './redux/slices/postSlice'
import type { RootState } from './redux/store'




function App() {

  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { isAuthenticate } = useSelector((state: RootState) => state.app);

  const findByUsername = async (username: string) => {
    try {
      dispatch(setLoading(true))
      const response: RootEntity<UserDef> = await userService.findByUsername(username);
      if (response.status) {
        dispatch(setCurrentUser(response.data));
      }
    } catch (error: any) {
      toastService.showMessage(error.response.data.exception.message, ToastMessageType.ERROR);
    } finally {
      dispatch(setLoading(false));
    }
  }

  const getAccessToken = (): string | null => {
    return storageService.get(ACCESS_TOKEN);

  }

  const getPayloadInToken = (accessToken: string): string => {
    return jwtDecode(accessToken);
  }

  useEffect(() => {
    const accessToken: string | null = getAccessToken();
    if (accessToken == null) {
      storageService.clearAllTokenParameters();
      dispatch(setIsAuthenticate(false));
      navigate("/login");
      return;
    }
    const payload: any = getPayloadInToken(accessToken);
    findByUsername(payload.sub)
    dispatch(setIsAuthenticate(true));
  }, []);


  const findAllPost = async () => {
    const response: RootEntity<PostType[]> = await postService.findAllPost();
    if (response.status) {
      dispatch(setPosts(response.data));
    }
  }

  useEffect(() => {
    if (isAuthenticate) {
      findAllPost();
    }
  }, []);



  return (
    <div>
      <div>
        <RouterConfig />
        <Spinner />
      </div>
    </div>
  )
}

export default App
