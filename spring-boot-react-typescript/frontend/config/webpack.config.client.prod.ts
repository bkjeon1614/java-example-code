import * as path from 'path'
import * as webpack from 'webpack'
import * as autoprefixer from 'autoprefixer'
import * as HtmlWebpackPlugin from 'html-webpack-plugin'
import * as ManifestPlugin from 'webpack-manifest-plugin'
import * as UglifyJsPlugin from 'uglifyjs-webpack-plugin'

const SWPrecacheWebpackPlugin = require('sw-precache-webpack-plugin')
const eslintFormatter = require('react-dev-utils/eslintFormatter')
const ModuleScopePlugin = require('react-dev-utils/ModuleScopePlugin')

import getClientEnvironment from './env'
import getBaseWebpackConfig, { CustomizedWebpackConfiguration } from './webpack.config.base'

export { CustomizedWebpackConfiguration }

export default function getWebpackClientProdConfig(config: CustomizedWebpackConfiguration) {
  const base = getBaseWebpackConfig(config)

  const webpackConfig = { ...base }

  const paths = config.paths

  // Webpack uses `publicPath` to determine where the app is being served from.
  // It requires a trailing slash, or the file assets will get an incorrect path.
  const publicPath = paths.servedPath
  // Some apps do not use client-side routing with pushState.
  // For these, "homepage" can be set to "." to enable relative asset paths.
  const shouldUseRelativeAssetPaths = publicPath === './'
  // `publicUrl` is just like `publicPath`, but we will provide it to our app
  // as %PUBLIC_URL% in `index.html` and `process.env.PUBLIC_URL` in JavaScript.
  // Omit trailing slash as %PUBLIC_URL%/xyz looks better than %PUBLIC_URL%xyz.
  const publicUrl = publicPath.slice(0, -1)
  // Get environment variables to inject into our app.
  const env = getClientEnvironment(publicUrl, true, config)

  // Assert this just to be safe.
  // Development builds of React are slow and not intended for production.
  // if (env.stringified['process.env'].NODE_ENV !== '"production"') {
  //   throw new Error('Production builds must have NODE_ENV=production.')
  // }

  // Note: defined here because it will be used more than once.
  // const cssFromCssFilename = 'static/css/[name]-css.[contenthash:8].css';
  const cssFromSassFilename = 'static/css/[name]-sass.[contenthash:8].css'

  // ExtractTextPlugin expects the build output to be flat.
  // (See https://github.com/webpack-contrib/extract-text-webpack-plugin/issues/27)
  // However, our output is structured with css, js and media folders.
  // To have this structure working with relative paths, we have to use custom options.
  const extractTextPluginOptions = shouldUseRelativeAssetPaths
    ? // Making sure that the publicPath goes back to to build folder.
      { publicPath: Array(cssFromSassFilename.split('/').length).join('../') }
    : {}

  // This is the production configuration.
  // It compiles slowly and is focused on producing a fast and minimal bundle.
  // The development configuration is different and lives in a separate file.

  webpackConfig.mode = 'production'

  // In production, we only want to load the polyfills and the app code.
  webpackConfig.entry = [require.resolve('./polyfills'), paths.clientEntry]
  webpackConfig.output = {
    // We inferred the "public path" (such as / or /my-project) from homepage.
    publicPath,
    // The build folder.
    path: paths.clientBuild,
    // Generated JS file names (with nested folders).
    // There will be one main bundle, and one file per asynchronous chunk.
    // We don't currently advertise code splitting but Webpack supports it.
    filename: 'static/js/[name].[chunkhash:8].js',
    chunkFilename: 'static/js/[name].[chunkhash:8].chunk.js',
    // Point sourcemap entries to original disk location
    devtoolModuleFilenameTemplate: info => path.relative(paths.src, info.absoluteResourcePath),
  }
  webpackConfig.module.rules = webpackConfig.module.rules.concat([
    // Process TypeScript.
    {
      test: /\.tsx?$/,
      include: config.tsPaths,
      exclude: /node_modules/,
      loader: require.resolve('ts-loader'),
      options: {
        configFile: config.tsConfigPath,
        transpileOnly: false,
      },
    },
    // "postcss" loader applies autoprefixer to our CSS.
    // "css" loader resolves paths in CSS and adds assets as dependencies.
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
    // ** STOP ** Are you adding a new loader?
    // Remember to add the new extension(s) to the "file" loader exclusion list.
  ])

  webpackConfig.plugins = webpackConfig.plugins.concat([
    new webpack.DefinePlugin(env.stringified),
    // Note: this won't work without ExtractTextPlugin.extract(..) in `loaders`.
    // extractCss,
    // Generate a manifest file which contains a mapping of all asset filenames
    // to their corresponding output file so that tools can pick it up without
    // having to parse `index.html`.
    new ManifestPlugin({
      fileName: 'asset-manifest.json',
    }),
    // Generate a service worker script that will precache, and keep up to date,
    // the HTML & assets that are part of the Webpack build.
    new SWPrecacheWebpackPlugin({
      // By default, a cache-busting query parameter is appended to requests
      // used to populate the caches, to ensure the responses are fresh.
      // If a URL is already hashed by Webpack, then there is no concern
      // about it being stale, and the cache-busting can be skipped.
      dontCacheBustUrlsMatching: /\.\w{8}\./,
      filename: 'service-worker.js',
      logger(message) {
        if (message.indexOf('Total precache size is') === 0) {
          // This message occurs for every build and is a bit too noisy.
          return
        }
        console.log(message)
      },
      minify: true,
      // For unknown URLs, fallback to the index page
      navigateFallback: publicUrl + '/',
      // Ignores URLs starting from /__ (useful for Firebase):
      // https://github.com/facebookincubator/create-react-app/issues/2237#issuecomment-302693219
      navigateFallbackWhitelist: [/^(?!\/__).*/],
      // Don't precache sourcemaps (they're large) and build asset manifest:
      staticFileGlobsIgnorePatterns: [/\.map$/, /asset-manifest\.json$/],
      // Work around Windows path issue in SWPrecacheWebpackPlugin:
      // https://github.com/facebookincubator/create-react-app/issues/2235
      stripPrefix: paths.clientBuild.replace(/\\/g, '/') + '/',
    }),
    new HtmlWebpackPlugin({
      inject: true,
      template: paths.appHtml,
    }),
  ])

  webpackConfig.optimization = {
    minimizer: [
      new UglifyJsPlugin({
        parallel: true,
        uglifyOptions: {
          compress: {
            warnings: false,
            // Disabled because of an issue with Uglify breaking seemingly valid code:
            // https://github.com/facebookincubator/create-react-app/issues/2376
            // Pending further investigation:
            // https://github.com/mishoo/UglifyJS2/issues/2011
            comparisons: false,
          },
          output: {
            comments: false,
          },
        },
        sourceMap: true,
      }),
    ],
  }

  // Some libraries import Node modules but don't use them in the browser.
  // Tell Webpack to provide empty mocks for them so importing them works.
  webpackConfig.node = {
    fs: 'empty',
    net: 'empty',
    tls: 'empty',
  }

  webpackConfig.stats = 'errors-only'

  return webpackConfig
}
