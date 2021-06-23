export interface ErrorResponse<T> {
  message: string
  errors: T[]
}
