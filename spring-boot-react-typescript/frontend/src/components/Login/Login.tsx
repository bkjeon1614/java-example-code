import * as React from 'react'
import LoginForm from './LoginForm'
import { Col, Layout, Row } from 'antd'

const { Header, Content, Footer } = Layout
const { useRef } = React

const Login = () => {
  const contentsWrapEl = useRef(null)

  return (
    <Layout>
      <Header className="header">
        <Row>
          <Col xs={{ span: 5, offset: 1 }} lg={{ span: 6, offset: 2 }} />
        </Row>
      </Header>
      <Content
        style={{
          background: '#fff',
          padding: 24,
          margin: 0,
          minHeight: 280,
        }}
      >
        <div>
          <div style={{ padding: '20px 10px' }} ref={contentsWrapEl}>
            <div style={{ display: 'table', width: '100%' }}>
              <img src="/images/react-logo.svg" />
            </div>
            <LoginForm />
          </div>
        </div>
      </Content>
      <Footer>
        <span>bkjeon</span>
      </Footer>
    </Layout>
  )
}
export default Login
