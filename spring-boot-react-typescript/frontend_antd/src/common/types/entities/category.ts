import { SearchOptionRange, SearchOptionTerm } from '../enum/searchOptions'

export interface Category {
  categoryId: string
  categoryName: string
  categoryImage?: string
  defaut?: boolean
  category?: Category[]
}

export interface CategoryFormResult {
  range?: SearchOptionRange
  term?: SearchOptionTerm
  firstCategoryId?: string
  secondCategoryId?: string
  thirdCategoryId?: string
}
