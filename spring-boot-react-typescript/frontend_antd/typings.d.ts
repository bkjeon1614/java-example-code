declare module '*.scss' {
  const classes: any
  export = classes
}

declare module '*.css' {
  const classes: any
  export = classes
}

declare global {
  interface Window {
    Promise: Promise
  }
}
