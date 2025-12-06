<script setup lang="ts">
interface Props {
  title?: string
  showBack?: boolean
}

withDefaults(defineProps<Props>(), {
  title: '标题文字',
  showBack: false,
})

function handleBack() {
  uni.navigateBack()
}
</script>

<template>
  <t-navbar
    v-if="showBack"
    t-class-placeholder="t-navbar-placeholder"
    t-class-content="t-navbar-content"
    :title="title"
    :fixed="true"
    left-arrow
    @go-back="handleBack"
  />
  <t-navbar
    v-else
    t-class-placeholder="t-navbar-placeholder"
    t-class-content="t-navbar-content"
    :title="title"
    :fixed="true"
  />
</template>

<style scoped>
</style>
