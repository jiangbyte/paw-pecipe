<script setup>
  defineProps({
    value: {
      type: Number,
      required: true
    }
  })
</script>

<template>
  <t-tag theme="primary">
    {{ value }}
  </t-tag>
</template>
