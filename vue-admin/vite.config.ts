import { resolve } from 'node:path'
import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import process from 'node:process'
import vueDevTools from 'vite-plugin-vue-devtools'

import UnoCSS from 'unocss/vite'
import Icons from 'unplugin-icons/vite'
import IconsResolver from 'unplugin-icons/resolver'
import Components from 'unplugin-vue-components/vite'
import AutoImport from 'unplugin-auto-import/vite'
import { TDesignResolver } from '@tdesign-vue-next/auto-import-resolver'

// https://vite.dev/config/
export default defineConfig(({ command, mode }) => {
  const env = loadEnv(mode, process.cwd(), '')
  console.log('Using API:', env.VITE_API_URL)
  return {
    plugins: [
      vue(),
      vueJsx(),
      vueDevTools(),
      UnoCSS(),
      AutoImport({
        imports: ['vue', 'vue-router', 'pinia'],
        resolvers: [
          TDesignResolver({
            library: 'vue-next'
          })
        ],
        include: [/\.[tj]sx?$/, /\.vue$/, /\.vue\?vue/, /\.md$/],
        dts: 'src/typings/auto-imports.d.ts'
      }),
      Components({
        dts: 'src/typings/components.d.ts',
        resolvers: [
          IconsResolver({
            prefix: false
          }),
          TDesignResolver({
            library: 'vue-next'
          })
        ]
      }),
      Icons({
        defaultStyle: 'display:inline-block',
        compiler: 'vue3'
      })
    ],
    resolve: {
      alias: {
        '@': resolve(__dirname, 'src')
      }
    },
    server: {
      host: '0.0.0.0',
      port: Number.parseInt(env.VITE_PORT) || 3000
    },
    esbuild:
      command === 'build'
        ? {
            drop: ['console', 'debugger']
          }
        : {}
  }
})
