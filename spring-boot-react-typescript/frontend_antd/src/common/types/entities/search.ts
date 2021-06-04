import { SearchOptionRange, SearchOptionTerm, AgeGroup, Gender } from '../enum/searchOptions'
import { Brand } from './brand'

export interface CategoryFormResult {
  range?: SearchOptionRange
  term?: SearchOptionTerm
  firstCategoryId?: string
  firstCategoryName?: string
  secondCategoryId?: string
  secondCategoryName?: string
  thirdCategoryId?: string
  thirdCategoryName?: string
}

export interface AgeFormResult {
  age?: AgeGroup
  gender?: Gender
  category?: CategoryFormResult
}

export interface BrandFormResult {
  name?: string
  brand?: Brand
  category?: CategoryFormResult
}

export interface SearchForm {
  searchword?: string
  categoryId?: string
  categoryName?: string
  brand?: string
  brandName?: string
  benefit?: string
  benefitName?: string
  startValue?: number
  endValue?: number
}
