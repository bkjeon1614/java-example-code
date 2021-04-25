import { ifElse } from './ifElse'

export const isProd = ifElse(process.env.NODE_ENV === 'production')
export const isProdEnv = isProd(true, false)
