import * as moment from 'moment'

export function dateFormatByMoment(date: Date | string | number, formatString = 'YYYY-MM-DD HH:mm:ss') {
  return moment(date).format('YYYY-MM-DD HH:mm:ss')
}

export function priceComma(price: any) {
  const priceString = price + ''
  if (priceString.indexOf(',') > -1) return price

  const result = new Array()
  priceString
    .split('')
    .reverse()
    .forEach(function(value, index) {
      result.push(value)
      const order = index + 1
      if (order % 3 === 0 && priceString.length > order) result.push(',')
    })
  return result.reverse()
}
