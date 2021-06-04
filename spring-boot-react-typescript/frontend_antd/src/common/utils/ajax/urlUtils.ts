import { isDev } from 'common/utils'

export const getBaseUrl = () => {
  // return isDev(devBaseUrl, '')

  if (process.env.NODE_ENV === 'development') {
    const config = require('../../../config').default
    const hostname = window && window.location.hostname
    const host = hostname && hostname !== 'localhost' ? hostname : config.host

    const devBaseUrl = `${config.protocol}://${host}${config.port ? `:${config.port}` : ''}`
    return devBaseUrl
  }
  return ''
}
