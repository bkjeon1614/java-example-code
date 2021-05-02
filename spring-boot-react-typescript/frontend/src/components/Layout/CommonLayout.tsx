import * as React from 'react'
import { withRouter } from 'react-router-dom'
import { DynamicCx } from 'common/types'
import { styling } from 'common/utils'
import { History } from 'history'
import * as s from './Layout.scss'
import PageNotFound from '../Error/PageNotFound'
import { Avatar, Breadcrumb, Col, Icon, Layout, Menu, Row } from 'antd'

const { SubMenu } = Menu
const { Header, Content, Sider } = Layout

interface IProps {
  cx?: DynamicCx
  children?: any
  history?: History
}

const CommonLayout = (props: IProps) => {
  const { cx, children, history } = props

  const pathName = history.location.pathname.replace('/app/', '')
  const pathNameArray = pathName.split('/')

  return (
    <Layout>
      <Header className="header">
        <Row>
          <Col xs={{ span: 5, offset: 1 }} lg={{ span: 6, offset: 2 }}>
            <div className={cx('logo_wrap')}>
              <div className={cx('logo')}>
                <img src="/images/react-logo.svg" />
              </div>
            </div>
          </Col>
          <Col xs={{ span: 11, offset: 1 }} lg={{ span: 6, offset: 2 }}>
            <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['2']} style={{ lineHeight: '64px' }}>
              {/*<Menu.Item key="1">소유권한 1</Menu.Item>*/}
            </Menu>
          </Col>
          <Col xs={{ span: 5, offset: 1 }} lg={{ span: 6, offset: 2 }}>
            <div className={cx('user_info')}>
              <Avatar shape="square" icon="user" />
              &nbsp; bkjeon 님
            </div>
          </Col>
        </Row>
      </Header>
      <Layout>
        <Sider width={200} style={{ background: '#fff' }}>
          <Menu
            mode="inline"
            defaultSelectedKeys={['1']}
            defaultOpenKeys={['sub1']}
            style={{ height: '100%', borderRight: 0 }}
          >
            <SubMenu
              key="sub1"
              title={
                <span>
                  <Icon type="user" />
                  subnav 1
                </span>
              }
            >
              <Menu.Item key="1">option1</Menu.Item>
              <Menu.Item key="2">option2</Menu.Item>
              <Menu.Item key="3">option3</Menu.Item>
              <Menu.Item key="4">option4</Menu.Item>
            </SubMenu>
            <SubMenu
              key="sub2"
              title={
                <span>
                  <Icon type="laptop" />
                  subnav 2
                </span>
              }
            >
              <Menu.Item key="5">option5</Menu.Item>
              <Menu.Item key="6">option6</Menu.Item>
              <Menu.Item key="7">option7</Menu.Item>
              <Menu.Item key="8">option8</Menu.Item>
            </SubMenu>
            <SubMenu
              key="sub3"
              title={
                <span>
                  <Icon type="notification" />
                  subnav 3
                </span>
              }
            >
              <Menu.Item key="9">option9</Menu.Item>
              <Menu.Item key="10">option10</Menu.Item>
              <Menu.Item key="11">option11</Menu.Item>
              <Menu.Item key="12">option12</Menu.Item>
            </SubMenu>
          </Menu>
        </Sider>
        <Layout style={{ padding: '0 24px 24px' }}>
          <Breadcrumb style={{ margin: '16px 0' }}>
            <Breadcrumb.Item>Home</Breadcrumb.Item>
            {pathNameArray.map((pathName, index) => (
              <Breadcrumb.Item key={`menu_path_${index}`}>{pathName}</Breadcrumb.Item>
            ))}
          </Breadcrumb>
          <Content
            style={{
              background: '#fff',
              padding: 24,
              margin: 0,
              minHeight: 280,
            }}
          >
            {children ? children : <PageNotFound />}
          </Content>
        </Layout>
      </Layout>
    </Layout>
  )
}
export default styling(s)(withRouter(CommonLayout))
