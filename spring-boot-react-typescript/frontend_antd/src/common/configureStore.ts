import { createStore, applyMiddleware, Middleware } from 'redux'
import reduxThunk from 'redux-thunk'
import { createLogger } from 'redux-logger'
import rootReducer from './reducer'
import { isBrowser, isDev } from './utils'
import { composeWithDevTools } from 'redux-devtools-extension'

export default function configureStore(initialState?) {
  const middlewares: Middleware[] = [reduxThunk]

  process.env.USE_REDUX_LOGGER &&
    isDev(
      isBrowser(() => {
        const reduxLogger = createLogger({
          duration: true,
          diff: true,
        })

        middlewares.push(reduxLogger)
      }, null),
      null,
    )()

  const store = createStore(rootReducer, initialState, composeWithDevTools(applyMiddleware(...middlewares)))

  if (module.hot) {
    module.hot.accept('./reducer', () => {
      const nextRootReducer = require('./reducer').default
      store.replaceReducer(nextRootReducer)
    })
  }

  return store
}
