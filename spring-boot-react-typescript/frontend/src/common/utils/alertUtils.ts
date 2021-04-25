import { Modal } from 'antd'

export const alertPopup = (content, title = 'Info') => {
  Modal.info({
    title,
    content,
    maskClosable: true,
  })
}

export const errorPopup = (content, title = 'Error') => {
  Modal.error({
    title,
    content,
    maskClosable: true,
  })
}
