import * as React from 'react'
import { RouteComponentProps, withRouter } from 'react-router-dom'
import Routes from './routes'
import Layout from './Layout/CommonLayout'
import 'moment/locale/ko'

const { hot } = require('react-hot-loader')

const App = (props: RouteComponentProps) => {
  const { location } = props

  let showLayout = true
  if (location.pathname === '/app/login' || location.pathname === '/app/registration') {
    showLayout = false
  }

  return (
    <>
      {showLayout ? (
        <Layout>
          <Routes />
        </Layout>
      ) : (
        <Routes />
      )}
    </>
  )
}
export default hot(module)(withRouter(App))
