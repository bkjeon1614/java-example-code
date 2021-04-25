export const isDefined = val => val != null
export const isFunction = val => typeof val === 'function'
export const noop = _ => {}

export const newScript = src => cb => {
  const scriptElem = document.createElement('script')
  let resultSrc = src

  if (typeof resultSrc === 'object') {
    // copy every property to the element
    for (const key in resultSrc) {
      if (Object.prototype.hasOwnProperty.call(resultSrc, key)) {
        scriptElem[key] = resultSrc[key]
      }
    }
    resultSrc = resultSrc.src
  } else {
    scriptElem.src = resultSrc
  }
  scriptElem.addEventListener('load', () => cb(null, resultSrc))
  scriptElem.addEventListener('error', () => cb(true, resultSrc))
  document.body.appendChild(scriptElem)
  return scriptElem
}

const keyIterator = cols => {
  const keys = Object.keys(cols)
  let i = -1
  return {
    next() {
      i = i + 1
      if (i >= keys.length) return null
      return keys[i]
    },
  }
}

// tasks should be a collection of thunk
export const parallel = (...tasks) => each => cb => {
  let hasError = false
  let successed = 0
  const ret = []
  const filteredTasks = tasks.filter(isFunction)

  if (filteredTasks.length <= 0) cb(null)
  else {
    filteredTasks.forEach((task, i) => {
      const thunk = task
      thunk((err, ...args) => {
        let resultArgs = args
        if (err) hasError = true
        else {
          // collect result
          if (resultArgs.length <= 1) resultArgs = resultArgs[0]

          ret[i] = resultArgs
          successed = successed + 1
        }

        if (isFunction(each)) each.call(null, err, resultArgs, i)

        if (hasError) cb(true)
        else if (filteredTasks.length === successed) {
          cb(null, ret)
        }
      })
    })
  }
}

// tasks should be a collection of thunk
export const series = (...tasks) => each => cb => {
  const filteredTasks = tasks.filter(val => val != null)
  const nextKey = keyIterator(filteredTasks)
  const nextThunk = () => {
    const key = nextKey.next()
    let thunk = filteredTasks[key]
    if (Array.isArray(thunk)) thunk = parallel.apply(null, thunk).call(null, each)
    return [+key, thunk] // convert `key` to number
  }
  let key
  let thunk
  let next = nextThunk()
  key = next[0]
  thunk = next[1]
  if (thunk == null) return cb(null)

  const ret = []
  const iterator = () => {
    thunk((err, ...args) => {
      let resultArgs = args
      if (resultArgs.length <= 1) resultArgs = resultArgs[0]
      if (isFunction(each)) each.call(null, err, resultArgs, key)

      if (err) cb(err)
      else {
        // collect result
        ret.push(resultArgs)

        next = nextThunk()
        key = next[0]
        thunk = next[1]
        if (thunk == null) return cb(null, ret) // finished
        iterator()
      }
    })
  }
  iterator()
}
