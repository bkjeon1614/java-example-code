export interface ProjectConfig {
  project: string
  protocol?: 'http' | 'https'
  host?: string
  port?: number
  clientPort?: number
}
