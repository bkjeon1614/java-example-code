import * as React from 'react'
import { Button, Col, Form, Icon, Input, Modal, Row } from 'antd'
import * as styles from './Login.scss'

// import { login } from '../../services/login/login'

interface IProps {
  form: any
  history?: History
  location?: Location
}

const buttonItemLayout = {
  wrapperCol: { span: 14, offset: 4 },
}

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 14 },
}

const LoginForm = (props: IProps) => {
  const { form } = props
  const { getFieldDecorator } = form

  const handleSubmit = e => {
    e.preventDefault()
    form.validateFields(async (err, values) => {
      if (!err) {
        Modal.success({ title: '로그인 성공', content: '로그인 성공' })
        /*
        const response = await login(values)
        if (response && response.result === 'SUCCESS') {
          // redirect to home
          console.log(response)
        } else {
          Modal.error({ title: '로그인 실패', content: response.message })
        }
         */
      } else {
        Modal.error({ title: '로그인 성공', content: '로그인 실패' })
      }
    })
  }

  return (
    <Form onSubmit={handleSubmit} className={styles.loginForm}>
      <Form.Item label="로그인 ID" {...formItemLayout}>
        {getFieldDecorator('loginId', {
          rules: [{ required: true, message: 'Please input your username!' }],
        })(<Input prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="LoginId" />)}
      </Form.Item>
      <Form.Item label="비밀번호" {...formItemLayout}>
        {getFieldDecorator('password', {
          rules: [{ required: true, message: 'Please input your Password!' }],
        })(
          <Input
            prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
            type="password"
            placeholder="Password"
          />,
        )}
      </Form.Item>
      <Form.Item {...buttonItemLayout}>
        <Row>
          <Col span={24} style={{ textAlign: 'center' }}>
            <Button type="primary" htmlType="submit" className={styles.loginFormButton}>
              Log in
            </Button>&nbsp; Or {/*<a href="/app/registration">register now!</a>*/}
          </Col>
        </Row>
      </Form.Item>
      <h5 style={{ textAlign: 'right' }}> ✨ https://bkjeon1614.tistory.com </h5>
    </Form>
  )
}
export default Form.create()(LoginForm)
