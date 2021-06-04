import * as React from 'react'
import * as ReactDOM from 'react-dom'
import { BrowserRouter } from 'react-router-dom'
import { Provider } from 'react-redux'

import registerServiceWorker from 'common/client/registerServiceWorker'
import { insertCss } from 'common/utils'

import App from './components'
import configureStore from 'common/configureStore'

const Context = require('react-context-component').default

const rootEl = document.getElementById('root') as HTMLElement

const render = async Component => {
  ReactDOM.render(
    <Context insertCss={insertCss}>
      <BrowserRouter>
        <Provider store={configureStore()}>
          <Component />
        </Provider>
      </BrowserRouter>
    </Context>,
    rootEl,
  )
}

render(App).then()
registerServiceWorker()
