import { errorPopup } from '../utils'
import { CONFLICT, UNPROCESSABLE_ENTITY } from 'http-status-codes'

export interface ValidateError {
  code: string
  codes?: string[]
  field: string
  objectName?: string
  rejectedValue: any
  defaultMessage: string
}

export type ValidateErrorHandler = (errors: ValidateError[]) => void

export const formApiErrorHandler = async (err, errorHandler: ValidateErrorHandler) => {
  if (err instanceof Error) {
    errorPopup(err)
  }
  const { status, data } = err

  if (status === UNPROCESSABLE_ENTITY) {
    data && errorHandler(data)
  } else if (status === CONFLICT) {
    errorHandler([
      {
        field: 'id',
        code: 'duplicated',
        defaultMessage: '이미 있는 번호입니다.',
        rejectedValue: '',
      },
    ])
  } else {
    errorPopup(err)
  }
}
