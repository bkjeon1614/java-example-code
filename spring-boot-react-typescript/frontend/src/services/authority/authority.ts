import { axios } from '../../common/utils'

export const fetchUserMenuAuthority = async () => {
  const response = await axios.get('/api/setting/authority')
  console.log('')
  return response && response.data && response.data.contents
}

export const fetchAuthorizedGroup = async menuNo => {
  const response = await axios.get('/api/setting/authorizedGroups', {
    params: {
      menuNo,
    },
  })
  return response && response.data && response.data.contents
}

export const fetchUnauthorizedGroup = async menuNo => {
  const response = await axios.get('/api/setting/unauthorizedGroups', {
    params: {
      menuNo,
    },
  })
  return response && response.data && response.data.contents
}

export const updateAuthority = async authority => {
  const response = await axios.put('/api/setting/authority', authority)
  return response && response.data && response.data.result
}
