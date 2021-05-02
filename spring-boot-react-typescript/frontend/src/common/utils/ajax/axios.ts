import originalAxios, { AxiosRequestConfig } from 'axios'
import handleError from './apiErrorHandler'
import { getBaseUrl } from './urlUtils'
import { Cookie } from '../cookieUtils'
import getCookie = Cookie.getCookie

export const axios = originalAxios.create({
  baseURL: getBaseUrl(),
  withCredentials: true,
})

axios.interceptors.request.use(
  config => {
    config.headers = { ...config.headers, ADM_CODE_001: getCookie('ADM_CODE_001') }
    return config
  },
  error => {
    return handleError(error)
  },
)

axios.interceptors.response.use(
  response => {
    return response
  },
  error => {
    return handleError(error)
  },
)

export const axiosFileDownload = originalAxios.create({
  baseURL: getBaseUrl(),
  responseType: 'blob',
})
axiosFileDownload.interceptors.response.use(null, error => {
  return handleError(error)
})

export interface CustomAxiosRequestConfig extends AxiosRequestConfig {
  onErrorHttpStatuses?: number[]
}
