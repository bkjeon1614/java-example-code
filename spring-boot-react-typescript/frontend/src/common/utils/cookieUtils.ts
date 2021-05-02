import { UserInfoState } from '../../components/Layout/ducks/UserInfo'

export namespace Cookie {
  export const getCookies = () => {
    if (!document) return
    const cookieStrings = document.cookie.split('; ')
    const cookies = new Object()
    cookieStrings.forEach(cookieString => {
      try {
        const cookieArray = cookieString.split('=')
        cookies[cookieArray[0]] = cookieArray[1]
      } catch (e) {}
    })
    return cookies
  }

  export const getCookie = (name: string) => {
    const cookies = getCookies()
    if (!cookies) return
    return cookies[name]
  }

  export const setCookieWithPath = (name: string, value: string, expires: number, path: string) => {
    if (!document) return
    const date = new Date()
    date.setTime(date.getTime() + expires * 24 * 60 * 60 * 1000)
    document.cookie = `${name}=${value};expires=${date.toUTCString()};path=${path}`
  }

  export const setCookie = (name: string, value: string, expires: number) => {
    setCookieWithPath(name, value, expires, '/')
  }

  export const setLoginCookie = (userParams: UserInfoState) => {
    if (!document) return
    document.cookie = `${'id'}=${userParams.id};expires=${'1'};path=${'/'}`
    document.cookie = `${'name'}=${userParams.name};expires=${'1'};path=${'/'}`
    document.cookie = `${'storeCode'}=${userParams.storeCode};expires=${'1'};path=${'/'}`
    document.cookie = `${'token'}=${userParams.token};expires=${'1'};path=${'/'}`
  }

  export const deleteUserCookie = () => {
    if (!document) return
    document.cookie = `${'id'}=${''};expires=${'-1'};path=${'/'}`
    document.cookie = `${'name'}=${''};expires=${'-1'};path=${'/'}`
    document.cookie = `${'storeCode'}=${''};expires=${'-1'};path=${'/'}`
    document.cookie = `${'token'}=${''};expires=${'-1'};path=${'/'}`
  }
}
