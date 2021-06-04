import getWebpackClientDevConfig, { CustomizedWebpackConfiguration } from '../../../config/webpack.config.client.dev'
import paths from './paths'

import config from '../../config'

const webpackConfig: CustomizedWebpackConfiguration = {
  paths,
  tsConfigPath: paths.clientTsConfig,
  cssPaths: paths.projectComponents,
  tsPaths: [
    paths.clientEntry,
    paths.projectCommon,
    paths.projectComponents,
    paths.projectClient,
    paths.projectConfig,
    paths.srcCommon,
  ],
  publicPaths: [paths.public, paths.projectComponents],
  project: config.project,
  protocol: config.protocol,
  host: config.host,
  clientPort: config.clientPort,
}

export default (extendConfig = {}) => getWebpackClientDevConfig({ ...webpackConfig, ...extendConfig })
