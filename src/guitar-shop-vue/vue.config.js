const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  //transpileDependencies: true

  // https://cli.vuejs.org/config/#devserver-proxy
  devServer: {
    port: 1969,
    proxy: {
      '/GuitarShop': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      }
    }
  }
})
