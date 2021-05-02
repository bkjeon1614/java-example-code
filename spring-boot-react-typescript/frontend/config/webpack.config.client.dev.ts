import * as path from 'path'
import * as os from 'os'
import * as webpack from 'webpack'
import * as autoprefixer from 'autoprefixer'
import * as CaseSensitivePathsPlugin from 'case-sensitive-paths-webpack-plugin'
import * as CopyWebpackPlugin from 'copy-webpack-plugin'
import * as HtmlWebpackPlugin from 'html-webpack-plugin'
import getClientEnvironment from './env'
import getWebpackBaseConfig, { CustomizedWebpackConfiguration } from './webpack.config.base'

const WatchMissingNodeModulesPlugin = require('react-dev-utils/WatchMissingNodeModulesPlugin')

export { CustomizedWebpackConfiguration }

export default function getWebpackClientDevConfig(config: CustomizedWebpackConfiguration) {
  const base = getWebpackBaseConfig(config)

  const webpackConfig = { ...base }

  const paths = config.paths

  const publicUrl = ''
  // Get environment variables to inject into our app.
  const env = getClientEnvironment(publicUrl, true, config)

  webpackConfig.mode = 'development'
  webpackConfig.entry = [
    // Include an alternative client for WebpackDevServer. A client's job is to
    // connect to WebpackDevServer by a socket and get notified about changes.
    // When you save a file, the client will either apply hot updates (in case
    // of CSS changes), or refresh the page (in case of JS changes). When you
    // make a syntax error, this client will display a syntax error overlay.
    // Note: instead of the default WebpackDevServer client, we use a custom one
    // to bring better experience for Create React App users. You can replace
    // the line below with these two lines if you prefer the stock client:
    // require.resolve('webpack-dev-server/client') + '?/',
    // require.resolve('webpack/hot/only-dev-server'),
    // We ship a few polyfills by default:
    require.resolve('./polyfills'),
    'react-hot-loader/patch',
    `webpack-hot-middleware/client?reload=true&path=${config.protocol}://${config.host}:${
      config.clientPort
    }/__webpack_hmr`,
    // Errors should be considered fatal in development
    require.resolve('react-error-overlay'),
    // Finally, this is your app's code:
    paths.clientEntry,
    // We include the app code last so that if there is a runtime error during
    // initialization, it doesn't blow up the WebpackDevServer client, and
    // changing JS code would still trigger a refresh.
  ]

  webpackConfig.output = {
    // Next line is not used in dev but WebpackDevServer crashes without it:
    path: paths.clientBuild,
    // Add /* filename */ comments to generated require()s in the output.
    pathinfo: true,
    // This does not produce a real file. It's just the virtual path that is
    // served by WebpackDevServer in development. This is the JS bundle
    // containing code from all our entry points, and the Webpack runtime.
    filename: 'static/js/bundle.js',
    // There are also additional JS chunk files if you use code splitting.
    chunkFilename: 'static/js/[name].chunk.js',
    // This is the URL that app is served from. We use "/" in development.
    // publicPath: `${config.protocol}://${config.host}:${config.clientPort}/`,
    publicPath: '/',
    hotUpdateChunkFilename: 'static/[id].[hash].hot-update.js',
    hotUpdateMainFilename: 'static/[hash].hot-update.json',
    // Point sourcemap entries to original disk location
    devtoolModuleFilenameTemplate: info => path.resolve(info.absoluteResourcePath),
  }

  webpackConfig.module.rules = webpackConfig.module.rules.concat([
    // Process TypeScript.
    {
      test: /\.tsx?$/,
      include: config.tsPaths,
      exclude: /node_modules/,
      use: [
        {
          loader: require.resolve('cache-loader'),
          options: {
            cacheDirectory: path.resolve('.cache/cache-loader'),
          },
        },
        {
          loader: require.resolve('thread-loader'),
          options: {
            workers: os.cpus().length,
          },
        },
        {
          loader: require.resolve('babel-loader'),
          options: {
            cacheDirectory: true,
          },
        },
        {
          loader: require.resolve('ts-loader'),
          options: {
            configFile: config.tsConfigPath,
            happyPackMode: true,
          },
        },
      ],
    },
    // "postcss" loader applies autoprefixer to our CSS.
    // "css" loader resolves paths in CSS and adds assets as dependencies.
    // "style" loader turns CSS into JS modules that inject <style> tags.
    {
      test: /\.css$/,
      include: config.cssPaths,
      exclude: /node_modules/,
      use: [
        require.resolve('isomorphic-style-loader'),
        {
          loader: require.resolve('css-loader'),
          options: {
            importLoaders: 1,
            module: true,
            localIdentName: '[name]__[local]__[hash:base64:5]',
          },
        },
        {
          loader: require.resolve('postcss-loader'),
          options: {
            ident: 'postcss', // https://webpack.js.org/guides/migrating/#complex-options
            plugins: () => [
              require('postcss-flexbugs-fixes'),
              autoprefixer({
                browsers: [
                  '>1%',
                  'last 4 versions',
                  'Firefox ESR',
                  'not ie < 9', // React doesn't support IE8 anyway
                ],
                flexbox: 'no-2009',
              }),
            ],
          },
        },
      ],
    },
    {
      test: /\.scss$/,
      include: config.cssPaths,
      exclude: /node_modules/,
      use: [
        require.resolve('isomorphic-style-loader'),
        {
          loader: require.resolve('css-loader'),
          options: {
            importLoaders: 2,
            module: true,
            localIdentName: '[name]__[local]__[hash:base64:5]',
          },
        },
        {
          loader: require.resolve('postcss-loader'),
          options: {
            ident: 'postcss', // https://webpack.js.org/guides/migrating/#complex-options
            plugins: () => [
              require('postcss-flexbugs-fixes'),
              autoprefixer({
                browsers: [
                  '>1%',
                  'last 4 versions',
                  'Firefox ESR',
                  'not ie < 9', // React doesn't support IE8 anyway
                ],
                flexbox: 'no-2009',
              }),
            ],
          },
        },
        require.resolve('sass-loader'),
      ],
    },
  ])

  webpackConfig.plugins = webpackConfig.plugins.concat([
    new webpack.DefinePlugin(env.stringified),
    // This is necessary to emit hot updates (currently CSS only):
    new webpack.HotModuleReplacementPlugin(),
    new webpack.NamedModulesPlugin(),
    new webpack.NoEmitOnErrorsPlugin(),
    // Watcher doesn't work well if you mistype casing in a path so we use
    // a plugin that prints an error when you attempt to do this.
    // See https://github.com/facebookincubator/create-react-app/issues/240
    new CaseSensitivePathsPlugin(),
    // If you require a missing module and then `npm install` it, you still have
    // to restart the development server for Webpack to discover it. This plugin
    // makes the discovery automatic so you don't have to restart.
    // See https://github.com/facebookincubator/create-react-app/issues/186
    new CopyWebpackPlugin([
      {
        from: paths.public,
        to: paths.clientBuild,
        toType: 'dir',
      },
    ]),
    new HtmlWebpackPlugin({
      inject: true,
      template: paths.appHtml,
    }),
  ])

  webpackConfig.optimization = {
    namedModules: true,
    noEmitOnErrors: true,
  }

  // Turn off performance hints during development because we don't do any
  // splitting or minification in interest of speed. These warnings become
  // cumbersome.
  webpackConfig.performance = {
    hints: false,
  }

  // Some libraries import Node modules but don't use them in the browser.
  // Tell Webpack to provide empty mocks for them so importing them works.
  webpackConfig.node = {
    fs: 'empty',
    net: 'empty',
    tls: 'empty',
  }

  return webpackConfig
}
