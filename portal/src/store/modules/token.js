
const state = {
  accessToken: null
}

// getters
const getters = {
  accessToken: state => state.accessToken,
}

// actions
const actions = {
  updateToken ({ commit, state }, accessToken) {
    commit('setAccessToken', accessToken)
  },
}

// mutations
const mutations = {
  setAccessToken (state, status) {
    state.accessToken = status
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
