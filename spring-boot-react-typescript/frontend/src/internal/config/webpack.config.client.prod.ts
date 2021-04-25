import getWebpackClientProdConfig, { CustomizedWebpackConfiguration } from '../../../config/webpack.config.client.prod'
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
}

export default getWebpackClientProdConfig(webpackConfig)
