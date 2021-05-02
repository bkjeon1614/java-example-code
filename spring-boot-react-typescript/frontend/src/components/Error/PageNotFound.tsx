import * as React from 'react'
import { Alert } from 'antd'

const PageNotFound: React.SFC<any> = () => {
  return (
    <div>
      <Alert message="Error" description="페이지를 찾을 수 없습니다." type="error" showIcon={true} />
    </div>
  )
}

export default PageNotFound
