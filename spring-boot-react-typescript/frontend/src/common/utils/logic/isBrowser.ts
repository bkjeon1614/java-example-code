import { ifElse } from './ifElse'

export const isBrowser = ifElse(process.env.IS_BROWSER)
