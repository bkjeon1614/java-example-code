import start, { StartConfiguration } from '../../../config/scripts/start'
import paths from '../config/paths'
import webpackClientConfig from '../config/webpack.config.client.dev'

import config from '../../config'

console.log(config)

const startConfig: StartConfiguration = {
  paths,
  webpackClientConfig: webpackClientConfig(),
  clientPort: config.clientPort,
  serverPort: config.port,
  host: config.host,
  protocol: config.protocol,
}

start(startConfig)
