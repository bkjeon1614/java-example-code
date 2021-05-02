import * as React from 'react'
import { Redirect, Route, Switch } from 'react-router-dom'
import { Alert, Spin } from 'antd'
import Loadable from 'react-loadable'

const Loading = () => {
  return (
    <Spin tip="불러오는 중...">
      <Alert message="데이터를 불러오고 있어요!" description="가끔 서버가 응답을 하지 않으면... 하아..." type="info" />
    </Spin>
  )
}

const createLoadable = componentImport => {
  return Loadable({
    loader: componentImport,
    loading: Loading,
  })
}

export const Login = createLoadable(() => import('./Login/Login'))
export const Registration = createLoadable(() => import('./Registration/Registration'))
export const Home = createLoadable(() => import('./Home/Home'))

export const PageNotFound = createLoadable(() => import('./Error/PageNotFound'))

const Routes = () => {
  return (
    <>
      <Switch>
        <Redirect exact={true} from="" to="/app/login" />
        <Redirect exact={true} from="/" to="/app/login" />
        <Redirect exact={true} from="/app" to="/app/login" />
        <Route path="/app/login" component={Login} />
        <Route path="/app/registration" component={Registration} />
        <Route path="/app/home" component={Home} />

        <Route component={PageNotFound} />
      </Switch>
    </>
  )
}
export default Routes
