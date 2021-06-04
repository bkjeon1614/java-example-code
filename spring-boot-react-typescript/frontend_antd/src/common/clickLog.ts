import { getCookies, DisplayInfo, getDisplaySize, requestIFrame } from './utils/browserUtils'

export interface ClickLogParams {
  cd: string // code
  iw?: number // display inner width
  iy?: number // display inner height
  dw?: number // document width
  dy?: number // document height
  cx?: number // click position x
  cy?: number // click position y
  bu?: string // browser unique id
  uid?: string // user id (optional)
  kwd?: string // search keyword (optional)
  gc?: string // product id a.k.a. goodsCode (optional)
  i?: number // click index no (optional)
  r?: number // expose rank (optional)
}

export const getParamString = (params: Object) => {
  const result = []
  Object.keys(params).forEach((key, index) => {
    if (index === 0) result.push('?')
    else result.push('&')
    result.push(`${key}=${params[key]}`)
  })
  return result.join('')
}

const devClickLogUrl = 'http://localhost:8800/logging/click.png'
const prodClickLogUrl = ''
const getClickLogUrl = () => {
  const isDev = process.env.NODE_ENV === 'development'
  if (isDev) return devClickLogUrl
  return prodClickLogUrl
}

export const sclog = (code: string, userId?: string) => () => {
  clog({ cd: code, uid: userId }, event)
}

export const kclog = (code: string, keyword: string, goodsCode?: string, userId?: string) => () => {
  clog(
    {
      cd: code,
      kwd: keyword,
      gc: goodsCode,
      uid: userId,
    },
    event,
  )
}

export const pclog = (code: string, goodsCode: string, userId?: string) => () => {
  clog(
    {
      cd: code,
      gc: goodsCode,
      uid: userId,
    },
    event,
  )
}

export const dclog = (params: ClickLogParams) => () => {
  clog(params, event)
}

export const clog = (params: ClickLogParams, event: Event) => {
  if (event) {
    params['cx'] = event['x']
    params['cy'] = event['y']
  }
  const cookies = getCookies()
  params['bu'] = cookies['PCID']
  const displayInfo: DisplayInfo = getDisplaySize()
  params['iw'] = displayInfo.clientWidth
  params['ih'] = displayInfo.clientHeight
  params['dw'] = displayInfo.scrollWidth
  params['dh'] = displayInfo.scrollHeight
  const targetUrl = getClickLogUrl()
  requestIFrame(targetUrl + getParamString(params))
}
