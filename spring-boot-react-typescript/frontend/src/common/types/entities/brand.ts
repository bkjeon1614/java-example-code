export interface Brand {
  id: string
  name: string
  imageUrl?: string
}

export interface BrandParams {
  brandCode: string
  brandName: string
  checked: boolean
}
