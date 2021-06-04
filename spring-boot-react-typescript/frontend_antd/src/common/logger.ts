const trace: Console['trace'] = (message, ...args) => {
  process.env.NODE_ENV !== 'production' && console.trace(message, ...args)
}

const debug: Console['debug'] = (message, ...args) => {
  process.env.NODE_ENV !== 'production' && console.debug(message, ...args)
}

const info: Console['info'] = (message, ...args) => {
  process.env.NODE_ENV !== 'production' && console.info(message, ...args)
}

const warn: Console['warn'] = (message, ...args) => {
  process.env.NODE_ENV !== 'production' && console.warn(message, ...args)
}

const error: Console['error'] = (message, ...args) => {
  console.error(message, ...args)
}

export default {
  trace,
  debug,
  info,
  warn,
  error,
}
