// inspired from https://github.com/ctrlplusb/react-universally

export { isDev } from './logic/isDev'
export { isProd } from './logic/isProd'
export { isBrowser } from './logic/isBrowser'
export { ifElse } from './logic/ifElse'

export { styling } from './component/styling'

export { scriptLoader } from './component/scriptLoader'

export { axios, axiosFileDownload, CustomAxiosRequestConfig } from './ajax/axios'
export { getBaseUrl } from './ajax/urlUtils'

export { dateFormatByMoment } from './logic/format'

/// minor utils
export { createCx } from './component/createCx'
export { insertCss } from './component/insertCss'

export { alertPopup, errorPopup } from './alertUtils'

export { ArrayUtils } from './arrayUtils'

export { StringUtils } from './stringUtils'
