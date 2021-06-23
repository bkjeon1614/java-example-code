export interface DynamicCx {
  (...className): any
  dynamic: Function
  remove: Function
}
