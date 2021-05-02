import * as path from 'path'
import * as fs from 'fs'
import * as url from 'url'

// Make sure any symlinks in the project folder are resolved:
// https://github.com/facebookincubator/create-react-app/issues/637
const appDirectory = fs.realpathSync(process.cwd())
export const resolveApp = relativePath => path.resolve(appDirectory, relativePath)

const envPublicUrl = process.env.PUBLIC_URL

function ensureSlash(path, needsSlash): string {
  const hasSlash = path.endsWith('/')
  if (hasSlash && !needsSlash) {
    return path.substr(path, path.length - 1)
  }

  if (!hasSlash && needsSlash) {
    return `${path}/`
  }

  return path
}

const getPublicUrl = packageJson => envPublicUrl || require(packageJson).homepage

// We use `PUBLIC_URL` environment variable or "homepage" field to infer
// "public path" at which the app is served.
// Webpack needs to know it to put the right <script> hrefs into HTML even in
// single-page apps that may serve index.html for nested URLs like /todos/42.
// We can't use a relative path in HTML because we don't want to load something
// like /todos/42/static/js/bundle.7289d.js. We have to know the root.
function getServedPath(packageJson) {
  const publicUrl = getPublicUrl(packageJson)
  const servedUrl = envPublicUrl || (publicUrl ? url.parse(publicUrl).pathname : '/')
  return ensureSlash(servedUrl, true)
}

interface BasePaths {
  dotenv: string
  src: string
  srcCommon: string
  packageJson: string
  yarnLockFile: string
  testsSetup: string
  nodeModules: string
  tsConfig: string
  publicUrl: string
  servedPath: string
  appHtml: string
}

export interface Paths extends BasePaths {
  clientBuild: string
  clientEntry: string
  projectCommon: string
  projectComponents: string
  projectClient: string
  projectConfig: string
  public: string
  clientTsConfig: string
}

// config after eject: we're in ./config/
const basePaths: BasePaths = {
  dotenv: resolveApp('.env'),
  src: resolveApp('src'),
  srcCommon: resolveApp('src/common'),
  packageJson: resolveApp('package.json'),
  yarnLockFile: resolveApp('yarn.lock'),
  testsSetup: resolveApp('src/setupTests.ts'),
  nodeModules: resolveApp('node_modules'),
  tsConfig: resolveApp('tsconfig.json'),
  publicUrl: getPublicUrl(resolveApp('package.json')),
  servedPath: getServedPath(resolveApp('package.json')),
  appHtml: resolveApp('src/public/index.html'),
}

export default basePaths
