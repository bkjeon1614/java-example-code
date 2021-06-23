import * as fetch from 'node-fetch'
import * as mkdirp from 'mkdirp'
import * as fs from 'fs'
import logger from './src/common/logger'

process.env.BABEL_ENV = 'development'
process.env.NODE_ENV = 'development'

require('./config/env')

let config = require('./src/config').default

const apiUrl = `${config.protocol}://${config.host}:${config.port}/api/typescript/entities`
const dir = 'src/common/types/entities/generated'

mkdirp(dir, async err => {
  const resp = await fetch(apiUrl)
  const entityList = await resp.json()

  const files = fs.readdirSync(dir)
  files.map(file => {
    fs.unlink(dir + '/' + file, err => {
      if (err) {
        logger.error(err)
      }
      logger.info('successfully deleted ..', file)
    })
  })

  Promise.all(entityList.map(entityName => fetch(`${apiUrl}/${entityName}`).then(resp => resp.text()))).then(texts => {
    texts.forEach((text, index) => {
      logger.info('generating typescript interface...', entityList[index])
      fs.writeFile(`${dir}/${entityList[index]}.ts`, texts[index], err => {
        if (err) {
          logger.error(err)
        }
      })
    })
  })

  let indexText = ''
  entityList.map(entityName => {
    indexText += `export * from './${entityName}'\n`
  })
  if (indexText !== '') {
    fs.writeFile(`${dir}/index.ts`, indexText, err => {
      if (err) {
        logger.error(err)
      }
    })
  }
})
