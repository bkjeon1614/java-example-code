export interface Notice {
  itemId: string
  title: string
  textContents: string
  htmlContents: string
  regNm: string
  regDt: string
  lCateCode: string
  mCateCode: string
  hitCount: number
}

export interface ShopNotice {
  id?: number
  strCd: string
  text?: string
  regUsrId?: string
  modUsrId?: string
  delYn?: string
}
