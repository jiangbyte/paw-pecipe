<script lang="ts" setup>
  import { useAppStore, useRouterStore } from '@/stores'
  import { useRouter } from 'vue-router'

  const appStore = useAppStore()
  const routerStore = useRouterStore()
  const router = useRouter()
  const { collapsed } = storeToRefs(appStore)

  // 处理菜单点击
  function handleMenuClick(menu: any) {
    if (menu.menu_type === 1 && menu.external_url) {
      // 外链菜单
      if (menu.open_target === 1) {
        window.open(menu.external_url, '_blank')
      } else {
        window.location.href = menu.external_url
      }
    } else if (menu.menu_type === 2 && menu.redirect) {
      // 重定向菜单
      router.push(menu.redirect)
    } else if (menu.menu_type === 3 && menu.external_url) {
      // iframe 嵌入菜单
      // 这里可以根据你的 iframe 实现方式处理
      console.log('iframe menu:', menu)
    } else if (menu.path) {
      // 内部路由菜单
      router.push(menu.path)
    }
  }

  // 获取菜单路径
  function getMenuPath(menu: any) {
    return menu.path || menu.id
  }

  // 判断是否为外链菜单
  function isExternalMenu(menu: any) {
    return menu.menu_type === 1 && menu.external_url
  }
</script>

<template>
  <t-menu
    :value="routerStore.activeMenu"
    :collapsed="collapsed"
    :default-value="routerStore.menus[0]?.path"
  >
    <template #logo>
      <div class="w-full flex items-center justify-center" style="margin-left: 0">
        <img src="/logo3.png" height="45px" style="object-fit: cover" />
      </div>
    </template>

    <template #default>
      <template v-for="menu in routerStore.menus" :key="menu.id">
        <!-- 没有子菜单或者子菜单为空的情况 -->
        <template v-if="!menu.children || !menu.children.length">
          <!-- 外链菜单 -->
          <t-menu-item
            v-if="isExternalMenu(menu)"
            :name="getMenuPath(menu)"
            :value="getMenuPath(menu)"
            @click="handleMenuClick(menu)"
          >
            <template #icon>
              <t-icon :name="menu.meta.icon" />
            </template>
            {{ menu.meta.title }}
          </t-menu-item>

          <!-- 内部路由菜单 -->
          <t-menu-item
            v-else
            :name="getMenuPath(menu)"
            :value="getMenuPath(menu)"
            @click="handleMenuClick(menu)"
          >
            <template #icon>
              <t-icon :name="menu.meta.icon" />
            </template>
            {{ menu.meta.title }}
          </t-menu-item>
        </template>

        <!-- 有子菜单的情况 -->
        <t-submenu
          v-else
          :name="getMenuPath(menu)"
          :value="getMenuPath(menu)"
          :title="menu.meta.title"
        >
          <template #icon>
            <t-icon :name="menu.meta.icon" />
          </template>

          <!-- 递归子菜单 -->
          <template
            v-for="child in menu.children
              .filter(c => c.meta.visible)
              .sort((a, b) => Number(a.meta.sort) - Number(b.meta.sort))"
            :key="child.id"
          >
            <!-- 子菜单项为外链 -->
            <t-menu-item
              v-if="isExternalMenu(child)"
              :name="getMenuPath(child)"
              :value="getMenuPath(child)"
              @click="handleMenuClick(child)"
            >
              <template #icon>
                <t-icon :name="child.meta.icon" />
              </template>
              {{ child.meta.title }}
            </t-menu-item>

            <!-- 子菜单项为内部路由 -->
            <t-menu-item
              v-else
              :name="getMenuPath(child)"
              :value="getMenuPath(child)"
              @click="handleMenuClick(child)"
            >
              <template #icon>
                <t-icon :name="child.meta.icon" />
              </template>
              {{ child.meta.title }}
            </t-menu-item>
          </template>
        </t-submenu>
      </template>
    </template>

    <!-- <template #operations>
      <t-button
        shape="square"
        variant="text"
        @click="appStore.toggleCollapse"
      >
        <icon-park-outline-menu-fold-one v-if="appStore.collapsed" />
        <icon-park-outline-menu-unfold-one v-else />
      </t-button>
    </template> -->
  </t-menu>
</template>

<style scoped></style>
