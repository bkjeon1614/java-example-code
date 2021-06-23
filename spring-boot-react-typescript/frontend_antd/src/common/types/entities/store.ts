export interface Store {
  storeCode: string
  storeName: string
  phoneNumber: string
  distance: string
  remainStock: string
  lat: string
  lng: string
}

export interface StockSearchForm {
  goodsCode: string
  distance: string
  address: string
  storeCode: string
}
