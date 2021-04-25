import { RouteProps } from 'react-router-dom'

export interface Menu extends RouteProps {
  title: string
  className?: string
  subLinks?: Menu[]
}
