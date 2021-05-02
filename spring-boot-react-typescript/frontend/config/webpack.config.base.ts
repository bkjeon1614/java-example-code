import * as path from 'path'
import * as webpack from 'webpack'
import { Paths } from './paths'

const ModuleScopePlugin = require('react-dev-utils/ModuleScopePlugin')

const PROD = process.env.NODE_ENV === 'production'

export interface CustomizedWebpackConfiguration {
  paths: Paths
  tsConfigPath: string
  cssPaths: string | string[]
  tsPaths: string | string[]
  publicPaths: string | string[]
  project: string
  host?: string
  protocol?: string
  clientPort?: number
  useReduxLog?: string
}

export default function getWebpackBaseConfig(config: CustomizedWebpackConfiguration): any {
  const paths = config.paths

  // This is the development configuration.
  // It is focused on developer experience and fast rebuilds.
  // The production configuration is different and lives in a separate file.
  return {
    bail: PROD,
    // You may want 'eval' instead if you prefer to see the compiled output in DevTools.
    // See the discussion in https://github.com/facebookincubator/create-react-app/issues/343.
    devtool: PROD ? 'source-map' : 'cheap-module-source-map',
    output: {},
    resolve: {
      // This allows you to set a fallback for where Webpack should look for modules.
      // We placed these paths second because we want `node_modules` to "win"
      // if there are any conflicts. This matches Node resolution mechanism.
      // https://github.com/facebookincubator/create-react-app/issues/253
      modules: [paths.src, 'node_modules'],
      // These are the reasonable defaults supported by the Node ecosystem.
      // We also include JSX as a common component filename extension to support
      // some tools, although we do not recommend using it, see:
      // https://github.com/facebookincubator/create-react-app/issues/290
      extensions: ['.ts', '.tsx', '.js', '.json', '.jsx'],
      alias: {
        // Support React Native Web
        // https://www.smashingmagazine.com/2016/08/a-glimpse-into-the-future-with-react-native-for-web/
        'react-native': 'react-native-web',
      },
      plugins: [
        // Prevents users from importing files from outside of src/ (or node_modules/).
        // This often causes confusion because we only process files within src/ with babel.
        // To fix this, we prevent you from importing files out of src/ -- if you'd like to,
        // please link the files into your node_modules/ and let module-resolution kick in.
        // Make sure your source files are compiled, as they will not be processed in any way.
        new ModuleScopePlugin(paths.src),
      ],
    },
    module: {
      strictExportPresence: false,
      rules: [
        // TODO: Disable require.ensure as it's not a standard language feature.
        // We are waiting for https://github.com/facebookincubator/create-react-app/issues/2176.
        // { parser: { requireEnsure: false } },

        // ** ADDING/UPDATING LOADERS **
        // The "file" loader handles all assets unless explicitly excluded.
        // The `exclude` list *must* be updated with every change to loader extensions.
        // When adding a new loader, you must add its `test`
        // as a new entry in the `exclude` list for "file" loader.
        {
          test: /\.tsx?$/,
          enforce: 'pre',
          loader: require.resolve('tslint-loader'),
          include: config.tsPaths,
          exclude: /node_modules/,
        },
        // "file" loader makes sure those assets get served by WebpackDevServer.
        // When you `import` an asset, you get its (virtual) filename.
        // In production, they would get copied to the `build` folder.
        {
          exclude: [/\.html$/, /\.jsx?$/, /\.tsx?$/, /\.s?css$/, /\.json$/, /\.bmp$/, /\.gif$/, /\.jpe?g$/, /\.png$/],
          include: config.publicPaths,
          loader: require.resolve('file-loader'),
          options: {
            name: 'static/media/[name].[hash:8].[ext]',
          },
        },
        // "url" loader works like "file" loader except that it embeds assets
        // smaller than specified limit in bytes as data URLs to avoid requests.
        // A missing `test` is equivalent to a match.
        {
          test: [/\.bmp$/, /\.gif$/, /\.jpe?g$/, /\.png$/],
          loader: require.resolve('url-loader'),
          options: {
            limit: 10000,
            name: 'static/media/[name].[hash:8].[ext]',
          },
          include: config.publicPaths,
        },
        // ** STOP ** Are you adding a new loader?
        // Remember to add the new extension(s) to the "file" loader exclusion list.
      ],
    },
    plugins: [
      // Moment.js is an extremely popular library that bundles large locale files
      // by default due to how Webpack interprets its code. This is a practical
      // solution that requires the user to opt into importing specific locales.
      // https://github.com/jmblog/how-to-optimize-momentjs-with-webpack
      // You can remove this if you don't use Moment.js:
      new webpack.IgnorePlugin(/^\.\/locale$/, /moment$/),
    ],
    stats: {
      warnings: false,
    },
  }
}
