const webpack = require('webpack');

module.exports = {
  lintOnSave: false,
  devServer: {
    // historyApiFallback: true,
    proxy: { // proxyTable 설정
      '/api': {
        target: "http://localhost:8080",
        changeOrigin: true,
        pathRewrite: {
          "^/api": ""
        }
        // logLevel: 'debug' // this what you want
      }
    }
  },
  configureWebpack: {
    // Set up all the aliases we use in our app.
    resolve: {
      alias: {
        'chart.js': 'chart.js/dist/Chart.js'
      }
    },
    plugins: [
      new webpack.optimize.LimitChunkCountPlugin({
        maxChunks: 6
      })
    ]
  },
  pwa: {
    name: 'Vue Black Dashboard',
    themeColor: '#344675',
    msTileColor: '#344675',
    appleMobileWebAppCapable: 'yes',
    appleMobileWebAppStatusBarStyle: '#344675'
  },
  pluginOptions: {
    i18n: {
      locale: 'en',
      fallbackLocale: 'en',
      localeDir: 'locales',
      enableInSFC: false
    }
  },
  css: {
    // Enable CSS source maps.
    sourceMap: process.env.NODE_ENV !== 'production'
  }
};
