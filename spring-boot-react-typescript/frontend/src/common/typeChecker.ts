interface TypeChecker {
  isArray?(value: any): boolean
  isObject?(value: any): boolean
  isString?(value: any): boolean
  isDate?(value: any): boolean
  isRegExp?(value: any): boolean
  isFunction?(value: any): boolean
  isBoolean?(value: any): boolean
  isNumber?(value: any): boolean
  isNull?(value: any): boolean
  isUndefined?(value: any): boolean
}

export default (() => {
  const typeChecker: TypeChecker = {}

  const types = 'Array Object String Date RegExp Function Boolean Number Null Undefined'.split(' ')

  function getType() {
    return Object.prototype.toString.call(this).slice(8, -1)
  }

  types.forEach(type => {
    typeChecker[`is${type}`] = (self => elem => getType.call(elem) === self)(type)
  })

  return typeChecker
})()
