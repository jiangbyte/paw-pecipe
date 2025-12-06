import { defineConfig, loadEnv } from 'vite'
import uni from '@dcloudio/vite-plugin-uni'
import process from 'node:process'

// https://vite.dev/config/
export default defineConfig(({ command, mode }) => {
  const env = loadEnv(mode, process.cwd(), '')
  console.log('Using API:', env.VITE_API_URL)
  console.log('Using ONNX API:', env.VITE_ONNX_API_URL)
  return {
    plugins: [uni()],
    esbuild:
      command === 'build'
        ? {
            drop: ['console', 'debugger'],
          }
        : {},
  }
})
