<script setup>
  defineProps({
    value: {
      type: Boolean,
      required: true
    }
  })
</script>

<template>
  <t-tag :theme="value ? 'primary' : 'danger'">
    {{ value ? '是' : '否' }}
  </t-tag>
</template>
