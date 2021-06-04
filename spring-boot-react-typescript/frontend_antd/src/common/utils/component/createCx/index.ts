import * as classNames from 'classnames/bind'

import createEnhancedCx from './createEnhancedCx'

export function createCx(styles: any[], displayName) {
  const allStyle: any = {}

  styles.forEach(style => {
    if (typeof style === 'object') {
      Object.entries(style).forEach(([key, value]) => {
        if (typeof value === 'string') {
          allStyle[key] = value
        }
      })
    }
  })

  const cx = classNames.bind(allStyle)

  return createEnhancedCx(cx, displayName)
}
