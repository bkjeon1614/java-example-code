// Do this as the first thing so that any code reading it knows the right env.
if (!process.env.NODE_ENV) {
  process.env.NODE_ENV = 'production'
}

// Makes the script crash on unhandled rejections instead of silently
// ignoring them. In the future, promise rejections that are not handled will
// terminate the Node.js process with a non-zero exit code.
process.on('unhandledRejection', err => {
  throw err
})

// Ensure environment variables are read.
require('../env')

import * as path from 'path'
import * as chalk from 'chalk'
import * as fs from 'fs-extra'
import * as webpack from 'webpack'

const checkRequiredFiles = require('react-dev-utils/checkRequiredFiles')
const formatWebpackMessages = require('react-dev-utils/formatWebpackMessages')
const printHostingInstructions = require('react-dev-utils/printHostingInstructions')
const FileSizeReporter = require('react-dev-utils/FileSizeReporter')
const printBuildError = require('react-dev-utils/printBuildError')

const measureFileSizesBeforeBuild = FileSizeReporter.measureFileSizesBeforeBuild
const printFileSizesAfterBuild = FileSizeReporter.printFileSizesAfterBuild

// These sizes are pretty large. We'll warn for bundles exceeding them.
const WARN_AFTER_BUNDLE_GZIP_SIZE = 512 * 1024
const WARN_AFTER_CHUNK_GZIP_SIZE = 1024 * 1024

export { BuildConfiguration } from '../build'

export default function buildClient(config) {
  const webpackConfig = config.webpackConfig
  const paths = config.paths

  const useYarn = fs.existsSync(paths.yarnLockFile)

  // Warn and crash if required files are missing
  if (!checkRequiredFiles([paths.clientEntry])) {
    process.exit(1)
  }

  // First, read the current file sizes in build directory.
  // This lets us display how much they changed later.
  measureFileSizesBeforeBuild(paths.clientBuild)
    .then(previousFileSizes => {
      // Remove all content but keep the directory so that
      // if you're in it, you don't end up in Trash
      fs.emptyDirSync(paths.clientBuild)
      // Merge with the public folder
      copyPublicFolder()
      // Start the webpack build
      return build(previousFileSizes)
    })
    .then(
      ({ stats, previousFileSizes, warnings }) => {
        if (warnings.length) {
          console.log(chalk.yellow('Compiled with warnings.\n'))
          console.log(warnings.join('\n\n'))
          console.log(
            '\nSearch for the ' + chalk.underline(chalk.yellow('keywords')) + ' to learn more about each warning.',
          )
          console.log('To ignore, add ' + chalk.cyan('// eslint-disable-next-line') + ' to the line before.\n')
        } else {
          console.log(chalk.green('Compiled successfully.\n'))
        }

        console.log('File sizes after gzip:\n')
        printFileSizesAfterBuild(
          stats,
          previousFileSizes,
          paths.clientBuild,
          WARN_AFTER_BUNDLE_GZIP_SIZE,
          WARN_AFTER_CHUNK_GZIP_SIZE,
        )
        console.log()

        const appPackage = require(paths.packageJson)
        const publicUrl = paths.publicUrl
        const publicPath = webpackConfig.output.publicPath
        const buildFolder = path.relative(process.cwd(), paths.clientBuild)
        printHostingInstructions(appPackage, publicUrl, publicPath, buildFolder, useYarn)
      },
      err => {
        console.log(chalk.red('Failed to compile.\n'))
        printBuildError(err)
        process.exit(1)
      },
    )

  // Create the production build and print the deployment instructions.
  function build(previousFileSizes) {
    console.log('Creating an optimized production build...')

    const compiler = webpack(webpackConfig)
    return new Promise((resolve, reject) => {
      compiler.run((err, stats: any) => {
        if (err) {
          return reject(err)
        }

        if (stats.compilation.errors.length) {
          console.log('client errors:', stats.compilation.errors)
        }

        const messages = formatWebpackMessages(stats.toJson({}, true))
        if (messages.errors.length) {
          // Only keep the first error. Others are often indicative
          // of the same problem, but confuse the reader with noise.
          if (messages.errors.length > 1) {
            messages.errors.length = 1
          }
          return reject(new Error(messages.errors.join('\n\n')))
        }
        if (
          process.env.CI &&
          (typeof process.env.CI !== 'string' || process.env.CI.toLowerCase() !== 'false') &&
          messages.warnings.length
        ) {
          console.log(
            chalk.yellow(
              '\nTreating warnings as errors because process.env.CI = true.\n' +
                'Most CI servers set it automatically.\n',
            ),
          )
          return reject(new Error(messages.warnings.join('\n\n')))
        }
        return resolve({
          stats,
          previousFileSizes,
          warnings: messages.warnings,
        })
      })
    })
  }

  function copyPublicFolder() {
    fs.copySync(paths.public, paths.clientBuild, {
      dereference: true,
    })
  }
}
