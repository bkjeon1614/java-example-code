import { axios } from '../../common/utils'

export const addMenu = async menu => {
  const response = await axios.post('/api/setting/menu', menu)
  return response && response.data && response.data.result
}

export const updateMenu = async menu => {
  const response = await axios.put('/api/setting/menu', menu)
  return response && response.data && response.data.result
}

export const deleteMenu = async menuNos => {
  const response = await axios.delete('/api/setting/menu', {
    data: {
      menuNos,
    },
  })
  return response && response.data && response.data.result
}
