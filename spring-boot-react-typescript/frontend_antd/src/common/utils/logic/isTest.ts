import { ifElse } from './ifElse'

export const isTest = ifElse(process.env.NODE_ENV === 'test')
export const isTestEnv = isTest(true, false)
