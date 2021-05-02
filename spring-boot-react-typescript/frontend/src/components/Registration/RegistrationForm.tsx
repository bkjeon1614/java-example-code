import * as React from 'react'
import { Button, Col, Form, Icon, Input, Row } from 'antd'
import * as styles from './Registration.scss'

interface IProps {
  form: any
}

const buttonItemLayout = {
  wrapperCol: { span: 14, offset: 4 },
}

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 14 },
}

const RegistrationForm = (props: IProps) => {
  const { form } = props
  const { getFieldDecorator } = form

  const handleSubmit = e => {
    e.preventDefault()
    form.validateFields(async (err, values) => {
      if (err) {
        console.log('로그인 실패')
      } else {
        console.log('로그인 성공')
        console.log(values)
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
      <Form.Item label="비밀번호 확인" {...formItemLayout}>
        {getFieldDecorator('passwordCheck', {
          rules: [{ required: true, message: 'Please input your PasswordCheck!' }],
        })(
          <Input
            prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
            type="passwordCheck"
            placeholder="password Check"
          />,
        )}
      </Form.Item>
      <Form.Item {...buttonItemLayout}>
        <Row>
          <Col span={24} style={{ textAlign: 'center' }}>
            <Button type="primary" htmlType="submit" className={styles.registrationFormButton}>
              Registration
            </Button>
          </Col>
        </Row>
      </Form.Item>
    </Form>
  )
}
export default Form.create()(RegistrationForm)
