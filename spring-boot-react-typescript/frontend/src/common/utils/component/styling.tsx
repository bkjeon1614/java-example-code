// forked and modified from 'isomorphic-style-loader/lib/withStyles'

import * as React from 'react'
import * as PropTypes from 'prop-types'

import { DynamicCx } from 'common/types/'
import { createCx } from '../'

const hoistStatics = require('hoist-non-react-statics')

function styling(...styles) {
  return function wrapStyling(ComposedComponent) {
    const displayName = ComposedComponent.displayName || ComposedComponent.name || 'Component'

    interface State {
      cx: DynamicCx
    }
    class Styling extends React.Component<any, State> {
      cssRemovers: Function[] = []
      staticStyles = []
      dynamicStyles = []

      static displayName = `Styling(${displayName})`
      static contextTypes = {
        insertCss: PropTypes.func,
      }
      static ComposedComponent = ComposedComponent

      constructor(props) {
        super(props)
        this.staticStyles = [...styles]

        this.state = { cx: this.createCx() }
      }

      componentWillUnmount() {
        if (this.cssRemovers.length > 0) {
          this.cssRemovers.forEach(removeCss => setTimeout(removeCss, 0))
        }
      }

      createCx() {
        const cx = createCx([...this.staticStyles, ...this.dynamicStyles], displayName) as any
        cx.dynamic = this.addDynamic
        cx.remove = this.removeDynamic

        return cx
      }

      updateCx() {
        this.setState({ cx: this.createCx() })
      }

      insertCss(styles: any[]) {
        try {
          this.cssRemovers = [...this.cssRemovers, this.context.insertCss(...styles)]
        } catch (err) {
          console.error(err, `This error from "${displayName}" component.`)
          throw err
        }
      }

      removeCss(key) {
        const staticRemoverLength = 1
        const dynamicKey = staticRemoverLength + key
        const removeCss = this.cssRemovers[dynamicKey]

        this.cssRemovers = [...this.cssRemovers.slice(0, dynamicKey), ...this.cssRemovers.slice(dynamicKey + 1)]

        setTimeout(removeCss, 0)
      }

      addDynamic = async (importedStyle: Promise<any>): Promise<number> => {
        const style = await importedStyle
        this.insertCss([style])
        this.dynamicStyles = [...this.dynamicStyles, style]

        this.updateCx()

        return this.dynamicStyles.length - 1
      }

      removeDynamic = key => {
        this.dynamicStyles = [...this.dynamicStyles.slice(0, key), ...this.dynamicStyles.slice(key + 1)]

        this.removeCss(key)
        this.updateCx()
      }

      render() {
        this.insertCss(this.staticStyles)
        const { cx } = this.state
        return <ComposedComponent {...this.props} {...{ cx }} />
      }
    }

    return hoistStatics(Styling, ComposedComponent)
  }
}

export { styling }
