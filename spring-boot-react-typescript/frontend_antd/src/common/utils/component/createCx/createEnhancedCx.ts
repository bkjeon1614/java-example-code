import * as classNames from 'classnames'

function isGlobalClass(className: string) {
  return className.startsWith('@')
}

export default function createEnhancedCx(cx, displayName) {
  return (...candidates) => {
    const components = []
    const globals = []

    candidates.forEach(candidate => {
      const candidateType = typeof candidate

      if (candidateType === 'string') {
        if (isGlobalClass(candidate)) {
          globals.push(candidate.slice(1))
        } else {
          components.push(candidate)
        }
      } else if (candidateType === 'object') {
        Object.entries(candidate).forEach(([className, condition]) => {
          if (condition) {
            if (isGlobalClass(className)) {
              globals.push(className.slice(1))
            } else {
              components.push(className)
            }
          }
        })
      } else {
        components.push(candidate)
      }
    })

    const componentClasses = cx(...components)
    const globalClasses = classNames(...globals)

    const classes = []

    if (componentClasses.length) classes.push(componentClasses)
    if (globalClasses.length) classes.push(globalClasses)

    return classes.join(' ')
  }
}
