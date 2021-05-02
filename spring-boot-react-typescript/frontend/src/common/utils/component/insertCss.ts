export const insertCss = (...styles) => {
  const removeCss = styles.map(x => x._insertCss())
  return () => {
    removeCss.forEach(f => f())
  }
}
