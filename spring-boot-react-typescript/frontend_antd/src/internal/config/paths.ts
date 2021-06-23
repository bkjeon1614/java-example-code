import * as path from 'path'
import * as fs from 'fs'

import basePaths, { Paths, resolveApp } from '../../../config/paths'

import config from '../../config'

const paths: Paths = {
  ...basePaths,
  ...{
    clientBuild: resolveApp(`build/client`),
    clientEntry: resolveApp(`src/client.tsx`),
    projectComponents: resolveApp(`src/components`),
    projectCommon: resolveApp(`src/common`),
    projectClient: resolveApp(`src/client`),
    projectConfig: resolveApp(`src/config`),
    public: resolveApp(`src/public`),
    clientTsConfig: resolveApp(`src/internal/config/tsconfig.client.json`),
  },
}

export default paths
