import * as React from 'react'
import { Card, Col, Divider, Icon, Progress, Row, Table } from 'antd'

const { Meta } = Card

const authority = [
  {
    key: '1',
    name: '관리자',
    desc: '모든 메뉴 접근 및 권한설정 가능',
  },
  {
    key: '2',
    name: '회원',
    desc: '일반회원',
  },
]

const columns = [
  {
    title: '권한명',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: '설명',
    dataIndex: 'desc',
    key: 'desc',
  },
]

const Home = () => {
  return (
    <div>
      <Row gutter={16}>
        <Col className="gutter-row" span={6}>
          <div className="gutter-box">
            <Divider orientation="left">사용자 정보</Divider>
            <Card
              style={{ width: 240, marginTop: 16 }}
              cover={<img alt="example" src="/images/react-logo.svg" />}
              actions={[
                <Icon type="setting" key="setting" />,
                <Icon type="edit" key="edit" />,
                <Icon type="ellipsis" key="ellipsis" />,
              ]}
            >
              <Meta title="bkjeon 님" description="https://bkjeon1614.tistory.com" />
            </Card>
          </div>
        </Col>
        <Col className="gutter-row" span={12}>
          <div className="gutter-box">
            <Divider orientation="left">보유 권한목록</Divider>
            <Table dataSource={authority} columns={columns} />
          </div>
        </Col>
        <Col className="gutter-row" span={6}>
          <div className="gutter-box">
            <Divider orientation="left">진척율</Divider>
            <div>
              <Progress percent={30} size="small" />
              <Progress percent={50} size="small" status="active" />
              <Progress percent={70} size="small" status="exception" />
              <Progress percent={100} size="small" />
            </div>
          </div>
        </Col>
      </Row>
    </div>
  )
}
export default Home
