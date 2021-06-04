export interface LayoutTitleState {
  title: string
}

const initialState: LayoutTitleState = {
  title: null,
}

const FETCH = 'Layout/LayoutTitle/FETCH'
const RESET = 'Layout/LayoutTitle/RESET'
const UPDATE = 'Layout/LayoutTitle/UPDATE'

export const fetchLayoutTitle = () => {
  return {
    type: FETCH,
  }
}

export const resetLayoutTitle = () => {
  return {
    type: RESET,
  }
}

export const updateLayoutTile = (title: string) => dispatch => {
  if (!title) return

  dispatch({
    state: {
      title,
    },
    type: UPDATE,
  })
}

const layoutTitleReducer = (state = initialState, action) => {
  switch (action.type) {
    case FETCH:
      return state
    case UPDATE:
      return {
        ...state,
        ...action.state,
      }
    case RESET:
      return initialState
    default:
      return state
  }
}

export default layoutTitleReducer
