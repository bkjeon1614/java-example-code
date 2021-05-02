export interface DisplayInfo {
  clientWidth: number
  clientHeight: number
  scrollWidth: number
  scrollHeight: number
}

export const getDisplaySize = () => {
  if (!window || !document) return null
  const documentElement = document.documentElement
  const documentBody = document.getElementsByTagName('body')[0]

  const x = window.innerWidth || documentElement.clientWidth || documentBody.clientWidth
  const y = window.innerHeight || documentElement.clientWidth || documentBody.clientHeight
  const w = documentElement.scrollWidth
  const h = documentElement.scrollHeight

  return {
    clientWidth: Number(x),
    clientHeight: Number(y),
    scrollWidth: Number(w),
    scrollHeight: Number(h),
  }
}

export const requestIFrame = (url: string) => {
  const iframe = document.createElement('iframe')

  iframe.style.display = 'none'
  iframe.src = url

  document.body.appendChild(iframe)
  setTimeout(() => {
    document.body.removeChild(iframe)
  }, 1000)
}

export const isScrollEnd = (marginBottom?: number) => {
  const displayInfo = getDisplaySize()
  const { clientHeight, scrollHeight } = displayInfo
  const currentPosition = window ? window.scrollY : 0
  return scrollHeight < currentPosition + clientHeight + (marginBottom || 0)
}
