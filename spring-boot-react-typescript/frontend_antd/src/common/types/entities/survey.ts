export interface SurveyParams {
  age: number
  gender: string
  score1: number[]
  score2: number[]
  score3: number[]
  selfReportUserNo: string
}

export interface SurveyQuestion {
  stepNo: string
  stepAlias: string
  stepName: string
  question: SurveyQuestion[]
}

export interface SurveyAnswer {
  typeNo: string
  typeName: string
  result: string
  typeSubName: string
}

export interface SmsParam {
  userNo: string
  phone: string
}
