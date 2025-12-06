<script lang="ts" setup>
  const mousePosition = ref({ x: 0, y: 0 })
  const showParticles = ref(false)

  // 监听鼠标移动，用于视差效果
  onMounted(() => {
    // 页面加载动画延迟
    setTimeout(() => {
      showParticles.value = true
    }, 500)

    const handleMouseMove = (e: MouseEvent) => {
      mousePosition.value = {
        x: e.clientX / window.innerWidth - 0.5,
        y: e.clientY / window.innerHeight - 0.5
      }
    }

    window.addEventListener('mousemove', handleMouseMove)
    return () => window.removeEventListener('mousemove', handleMouseMove)
  })
</script>

<template>
  <div class="page-container">
    <!-- 背景粒子效果 -->
    <div
      v-for="i in 20"
      :key="i"
      class="particle"
      :class="{ 'show-particle': showParticles }"
      :style="{
        left: `${Math.random() * 100}%`,
        top: `${Math.random() * 100}%`,
        width: `${Math.random() * 4 + 1}px`,
        height: `${Math.random() * 4 + 1}px`,
        animationDelay: `${Math.random() * 5}s`,
        animationDuration: `${Math.random() * 10 + 10}s`,
        transform: `translate(${mousePosition.x * 10}px, ${mousePosition.y * 10}px)`
      }"
    />

    <!-- 主要内容 -->
    <div
      class="content"
      :style="{
        transform: `translate(${mousePosition.x * 15}px, ${mousePosition.y * 15}px)`
      }"
    >
      <div class="error-code">
        4
        <span class="highlight">0</span>
        4
      </div>
      <h1 class="title">页面不存在</h1>
      <p class="message">抱歉，您访问的页面可能已被删除、移动或从未存在过</p>
      <button class="back-button" @click="$router.push('/')">
        返回首页
        <svg
          class="arrow-icon"
          xmlns="http://www.w3.org/2000/svg"
          width="16"
          height="16"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
        >
          <path d="m5 12 14-7-14-7" />
          <path d="M19 5v14" />
        </svg>
      </button>
    </div>

    <!-- 装饰元素 -->
    <div
      class="decor top-left"
      :style="{
        transform: `translate(${-mousePosition.x * 20}px, ${-mousePosition.y * 20}px)`
      }"
    />
    <div
      class="decor bottom-right"
      :style="{
        transform: `translate(${-mousePosition.x * 20}px, ${-mousePosition.y * 20}px)`
      }"
    />
  </div>
</template>

<style scoped>
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: 'Segoe UI', Roboto, Oxygen, Ubuntu, sans-serif;
  }

  .page-container {
    min-height: 100vh;
    background-color: #f8fafc;
    position: relative;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 2rem;
  }

  /* 粒子背景 */
  .particle {
    position: absolute;
    background-color: rgba(0, 99, 255, 0.1);
    border-radius: 50%;
    opacity: 0;
    transition: opacity 1s ease;
  }

  .show-particle {
    opacity: 1;
    animation: float 15s infinite ease-in-out;
  }

  @keyframes float {
    0%,
    100% {
      transform: translateY(0) translateX(0);
    }
    50% {
      transform: translateY(-20px) translateX(10px);
    }
  }

  /* 主要内容 */
  .content {
    text-align: center;
    position: relative;
    z-index: 10;
    transition: transform 0.1s ease-out;
    max-width: 600px;
  }

  .error-code {
    font-size: clamp(8rem, 20vw, 15rem);
    font-weight: 900;
    line-height: 1;
    color: #f1f5f9;
    position: relative;
    margin-bottom: 1rem;
  }

  .error-code::after {
    content: '404';
    position: absolute;
    top: 0;
    left: 0;
    color: #e2e8f0;
    transform: translate(8px, 8px);
    z-index: -1;
  }

  .highlight {
    color: #0063ff;
    position: relative;
  }

  .highlight::after {
    content: '0';
    color: rgba(0, 99, 255, 0.2);
    position: absolute;
    top: 8px;
    left: 8px;
    z-index: -1;
  }

  .title {
    font-size: clamp(1.5rem, 3vw, 2.5rem);
    color: #1e293b;
    margin-bottom: 1rem;
  }

  .message {
    color: #64748b;
    font-size: clamp(1rem, 2vw, 1.25rem);
    margin-bottom: 2.5rem;
    line-height: 1.6;
  }

  .back-button {
    background-color: #0063ff;
    color: white;
    border: none;
    padding: 0.875rem 2rem;
    font-size: 1rem;
    font-weight: 600;
    border-radius: 8px;
    cursor: pointer;
    display: inline-flex;
    align-items: center;
    gap: 0.5rem;
    transition: all 0.3s ease;
    box-shadow: 0 4px 12px rgba(0, 99, 255, 0.2);
  }

  .back-button:hover {
    background-color: #0056e0;
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(0, 99, 255, 0.3);
  }

  .back-button:active {
    transform: translateY(0);
    box-shadow: 0 2px 8px rgba(0, 99, 255, 0.2);
  }

  .arrow-icon {
    transition: transform 0.3s ease;
  }

  .back-button:hover .arrow-icon {
    transform: translateX(3px);
  }

  /* 装饰元素 */
  .decor {
    position: absolute;
    width: 300px;
    height: 300px;
    border-radius: 50%;
    z-index: 1;
    transition: transform 0.2s ease-out;
  }

  .top-left {
    top: -150px;
    left: -150px;
    background: radial-gradient(circle, rgba(0, 99, 255, 0.1) 0%, rgba(0, 99, 255, 0) 70%);
  }

  .bottom-right {
    bottom: -150px;
    right: -150px;
    background: radial-gradient(circle, rgba(0, 99, 255, 0.1) 0%, rgba(0, 99, 255, 0) 70%);
  }

  /* 响应式调整 */
  @media (max-width: 768px) {
    .decor {
      width: 200px;
      height: 200px;
    }

    .top-left {
      top: -100px;
      left: -100px;
    }

    .bottom-right {
      bottom: -100px;
      right: -100px;
    }
  }
</style>
