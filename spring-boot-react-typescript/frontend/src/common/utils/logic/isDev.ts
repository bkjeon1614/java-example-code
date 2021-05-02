import { ifElse } from './ifElse'

export const isDev = ifElse(process.env.NODE_ENV === 'development')
export const isDevEnv = isDev(true, false)
