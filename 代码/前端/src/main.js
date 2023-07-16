import { createApp  } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import '@/styles/common.scss'
import Vue3BaiduMapGL from 'vue3-baidu-map-gl'
import baiduMap from 'vue3-baidu-map-gl'


const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(Vue3BaiduMapGL)
app.use(baiduMap, {
    ak: 'XFgpGWBGm4vGvPIXpmmIPX8qFe3oH35u',
    plugins: ['TrackAnimation']
})
app.mount('#app')

