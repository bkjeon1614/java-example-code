const isEmpty = (arr: any[]): boolean => arr === undefined || arr === null || arr.length < 1

const isNotEmpty = (arr: any[]): boolean => !isEmpty(arr)

const getLast = (arr: any[], defaultValue?: any): any => {
  if (isEmpty(arr)) {
    return defaultValue
  }
  return arr[arr.length - 1]
}

const getFirst = (arr: any[], defaultValue?: any): any => {
  if (isEmpty(arr)) {
    return defaultValue
  }
  return arr[0]
}

/*
- hasSameElements(null, null) = false

- hasSameElements([1,2], null) = false

- hasSameElements([], []) = false

- hasSameElements([1,2], []) = false

- hasSameElements([1,2], [2,1]) = true
*/
const hasSameElements = (arr1, arr2) => {
  if (isEmpty(arr1) || isEmpty(arr2)) return false

  if (arr1.length !== arr2.length) return false

  return arr1.every(item1 => arr2.includes(item1))
}

export const ArrayUtils = { isEmpty, isNotEmpty, getFirst, getLast, hasSameElements }
