import { axios } from '../../common/utils'

export const login = async userParam => {
  const response = await axios.post('/api/login', userParam)
  return response && response.data
}
