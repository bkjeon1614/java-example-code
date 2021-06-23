import { axios } from '../../common/utils'

export const fetchGroups = async () => {
  const response = await axios.get('/api/setting/groups')
  return response && response.data && response.data.contents
}

export const fetchGroupUsers = async groupNo => {
  const response = await axios.get('api/setting/group/'.concat(groupNo, '/users'))
  return response && response.data && response.data.contents
}

export const deleteGroups = async groupNos => {
  const response = await axios.delete('api/setting/group', {
    data: {
      groupNos,
    },
  })
  return response && response.data
}

export const addGroup = async group => {
  const response = await axios.post('/api/setting/group', group)
  return response && response.data
}

export const updateGroup = async group => {
  const response = await axios.put('/api/setting/group', group)
  return response && response.data
}

export const addGroupUsers = async (groupNo, userNo) => {
  const response = await axios.post(''.concat('/api/setting/group/', groupNo, '/users/', userNo))
  return response && response.data
}

export const deleteGroupUsers = async (groupNo, userNo) => {
  const response = await axios.delete(''.concat('/api/setting/group/', groupNo, '/users/', userNo))
  return response && response.data
}
