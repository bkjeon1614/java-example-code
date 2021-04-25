import logger from 'common/logger'
import * as codes from 'http-status-codes'
import { ErrorResponse } from 'common/types/entities/errorResponse'
import { errorPopup } from 'common/utils'

const handleError = ({ response }) => {
  logging(response)

  if (response.status === 401) {
    if (confirm('로그인 후에 이용해주세요')) {
      window.location.href = '/app/login'
      return Promise.resolve(undefined)
    }
  }

  if (!response) {
    errorPopup('서버 연결에 실패했습니다.')
    return Promise.resolve(undefined)
  }

  if (isSelfErrorHandling(response)) {
    return Promise.reject(response)
  }
  if (isError(response)) {
    showAlert(response)
  }

  return Promise.resolve(undefined)
}

const isError = response => !response || response.status >= 400

const isSelfErrorHandling = response => {
  const selfErrorHandlingTarget = response && response.config.onErrorHttpStatuses
  return selfErrorHandlingTarget && selfErrorHandlingTarget.includes(response.status)
}

const logging = response => {
  logger.error('axios error occurred. ', response)
}

const showAlert = response => {
  const errorMessage = resolveErrorMessage(response)
  if (errorMessage) {
    errorPopup(errorMessage)
  }
}

const resolveErrorMessage = response => {
  const { status, config } = response
  const data: ErrorResponse<any> = response.data

  if (data && data.message) return data.message
  if (status === codes.FORBIDDEN) return '접근 권한이 없습니다.'
  if (status === codes.UNAUTHORIZED) return ' 로그인을 해주세요'
  if (status === codes.NOT_FOUND) return '페이지를 찾을 수 없습니다. 관리자에게 문의해주세요.'
  if (status === codes.CONFLICT) return '중복된 요청입니다.'
  if (status === codes.BAD_REQUEST || status === codes.UNPROCESSABLE_ENTITY) return '잘못된 요청입니다.'
  if (config.method === 'get') return '조회에 실패했습니다. 잠시 후 다시 시도해주세요.'

  return '처리에 실패했습니다. 잠시 후 다시 시도해주세요.'
}

export default handleError
