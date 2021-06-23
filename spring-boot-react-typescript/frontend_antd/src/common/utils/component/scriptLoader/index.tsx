/*
https://github.com/leozdgao/react-async-script-loader
A decorator for script lazy loading on react component
API

scriptLoader(...scriptSrc)([WrappedComponent])
scriptSrc can be a string of source or an array of source. scriptSrc will be loaded sequentially, but array of source will be loaded parallelly. It also cache the loaded script to avoid duplicated loading. More lively description see use case below.

Properties

Decorated component will receive following properties:

Name	Type	Description
isScriptLoaded	Boolean	Represent scripts loading process is over or not, maybe part of scripts load failed.
isScriptLoadSucceed	Boolean	Represent all scripts load successfully or not.
onScriptLoaded	Function	Triggered when all scripts load successfully.

How to use

You can use it to decorate your component.

import React, { Component } from 'react'
import scriptLoader from 'react-async-script-loader'

class Editor extends Component {
  ...

  componentWillReceiveProps ({ isScriptLoaded, isScriptLoadSucceed }) {
    if (isScriptLoaded && !this.props.isScriptLoaded) { // load finished
      if (isScriptLoadSucceed) {
        this.initEditor()
      }
      else this.props.onError()
    }
  }

  componentDidMount () {
    const { isScriptLoaded, isScriptLoadSucceed } = this.props
    if (isScriptLoaded && isScriptLoadSucceed) {
      this.initEditor()
    }
  }

  ...
}

export default scriptLoader(
  [
    'https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js',
    'https://cdnjs.cloudflare.com/ajax/libs/marked/0.3.5/marked.min.js'
  ],
  '/assets/bootstrap-markdown.js'
)(Editor)
*/
import * as React from 'react'
import * as PropTypes from 'prop-types'
import { newScript, series, noop } from './utils'

const loadedScript = []
const pendingScripts = {}
let failedScript = []

export function startLoadingScripts(scripts, onComplete = noop) {
  // sequence load
  const loadNewScript = script => {
    const src = typeof script === 'object' ? script.src : script
    if (loadedScript.indexOf(src) < 0) {
      return taskComplete => {
        const callbacks = pendingScripts[src] || []
        callbacks.push(taskComplete)
        pendingScripts[src] = callbacks
        if (callbacks.length === 1) {
          return newScript(script)(err => {
            pendingScripts[src].forEach(cb => cb(err, src))
            delete pendingScripts[src]
          })
        }
      }
    }
  }
  const tasks = scripts.map(src => {
    if (Array.isArray(src)) {
      return src.map(loadNewScript)
    }
    return loadNewScript(src)
  })

  series(...tasks)((err, src) => {
    if (err) {
      failedScript.push(src)
    } else {
      if (Array.isArray(src)) {
        src.forEach(addCache)
      } else addCache(src)
    }
  })(err => {
    removeFailedScript()
    onComplete(err)
  })
}

const addCache = entry => {
  if (loadedScript.indexOf(entry) < 0) {
    loadedScript.push(entry)
  }
}

const removeFailedScript = () => {
  if (failedScript.length > 0) {
    failedScript.forEach(script => {
      const node = document.querySelector(`script[src='${script}']`)
      if (node != null) {
        node.parentNode.removeChild(node)
      }
    })

    failedScript = []
  }
}

interface Props {
  onScriptLoaded(): void
}

const hoistStatics = require('hoist-non-react-statics')

function scriptLoader(...scripts) {
  return function wrapScript(WrappedComponent) {
    class ScriptLoader extends React.Component<Props, any> {
      _isMounted: boolean

      static propTypes = {
        onScriptLoaded: PropTypes.func,
      }

      static defaultProps = {
        onScriptLoaded: noop,
      }

      constructor(props, context) {
        super(props, context)

        this.state = {
          isScriptLoaded: false,
          isScriptLoadSucceed: false,
        }

        this._isMounted = false
      }

      componentDidMount() {
        this._isMounted = true
        startLoadingScripts(scripts, err => {
          if (this._isMounted) {
            this.setState(
              {
                isScriptLoaded: true,
                isScriptLoadSucceed: !err,
              },
              () => {
                if (!err) {
                  this.props.onScriptLoaded()
                }
              },
            )
          }
        })
      }

      componentWillUnmount() {
        this._isMounted = false
      }

      getWrappedInstance() {
        return this.refs.wrappedInstance
      }

      render() {
        const props = {
          ...this.props,
          ...this.state,
          ref: 'wrappedInstance',
        }

        return <WrappedComponent {...props} />
      }
    }

    return hoistStatics(ScriptLoader, WrappedComponent)
  }
}

export { scriptLoader }
