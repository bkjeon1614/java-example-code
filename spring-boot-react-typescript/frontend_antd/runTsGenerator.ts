const path = require('path')
const { execSync } = require('child_process')

console.log('### TypeScript Files Generating ###')
execSync(`${path.normalize('../../gradlew')} :shop-app:server:runTypeScriptGenerator`)
execSync(`cd ${path.normalize('../../')} && git add ${path.resolve('./', 'src/common/types/entities/generated')}/*.ts`)
