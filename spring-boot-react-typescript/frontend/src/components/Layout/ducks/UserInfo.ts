import { Cookie } from '../../../common/utils/cookieUtils'
import getCookie = Cookie.getCookie

export interface UserInfoState {
  id: string
  name: string
  storeCode: string
  token: string
}

const initialState: UserInfoState = {
  id: '',
  name: '',
  storeCode: '',
  token: '',
}

const FETCH = 'Layout/User/FETCH'
const RESET = 'Layout/User/RESET'
const UPDATE = 'Layout/User/UPDATE'

export const fetchUserInfo = () => dispatch => {
  const user: UserInfoState = {
    id: getCookie('id'),
    name: getCookie('name'),
    storeCode: getCookie('storeCode'),
    token: getCookie('token'),
  }
  dispatch({
    state: user,
    type: FETCH,
  })
}

export const resetUserInfo = () => {
  return {
    type: RESET,
  }
}

export const updateUserInfo = (UserInfoState: UserInfoState) => dispatch => {
  dispatch({
    state: UserInfoState,
    type: UPDATE,
  })
}

const userInfoReducer = (state = initialState, action) => {
  switch (action.type) {
    case FETCH:
      return action.state
    case RESET:
      return initialState
    case UPDATE:
      return action.state
    default:
      return state
  }
}

export default userInfoReducer
