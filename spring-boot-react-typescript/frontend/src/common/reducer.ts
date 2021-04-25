import { combineReducers } from 'redux'
import layoutTitle, { LayoutTitleState } from 'components/Layout/ducks/LayoutTitle'
import userInfo, { UserInfoState } from 'components/Layout/ducks/UserInfo'
import { LoginAnswer } from './types/entities/login'

export interface RootState {
  layoutTitle: LayoutTitleState
  loginAnswer: LoginAnswer
  userInfo: UserInfoState
}

export default combineReducers({
  layoutTitle,
  userInfo,
})
