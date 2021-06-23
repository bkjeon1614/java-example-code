import build, { BuildConfiguration } from '../../../config/scripts/build-client'
import paths from '../config/paths'
import webpackConfig from '../config/webpack.config.client.prod'

const buildConfig: BuildConfiguration = {
  paths,
  webpackConfig,
}

build(buildConfig)
