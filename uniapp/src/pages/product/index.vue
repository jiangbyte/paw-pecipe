<script lang="ts" setup>
import { useBizProductCategoryApi, useBizProductSkuApi } from '@/api'
import CategoryTabs from '@/components/CategoryTabs.vue'
import CustomNavbar from '@/components/CustomNavbar.vue'
import CustomTabBar from '@/components/CustomTabBar.vue'
import EmptyState from '@/components/EmptyState.vue'
import ProductCard from '@/components/ProductCard.vue'
import { ref } from 'vue'

const tabList = ref<any[]>([{
  value: '0',
  text: '全部',
}])

const pageData = ref<IListResult<any>>({
  current: 1,
  pages: 1,
  records: [],
  size: 20,
  total: 0,
})

const pageParams = ref({
  current: 1,
  pageSize: 20,
  sortField: 'id',
  sortOrder: 'DESC',
  keyword: '',
  categoryId: '0',
})

const activeTab = ref('0')
const noMore = ref(false)
const enable = ref(false)
const loading = ref(false)

useBizProductCategoryApi().GetLists().then(({ data, success }) => {
  if (success) {
    tabList.value.push(...data)
    console.log(data)
  }
})

async function loadPageData() {
  enable.value = true
  loading.value = true
  useBizProductSkuApi().PageBizProductSkuWithProduct(pageParams.value).then(({ data, success }) => {
    if (success) {
      pageData.value = data
      console.log(data)
      if (pageParams.value.current >= data.pages) {
        noMore.value = true
      }
    }
  }).catch((err) => {
    console.log(err)
  }).finally(() => {
    loading.value = false
    enable.value = false
    loading.value = false
  })
}
loadPageData()

function onChange(value: string) {
  console.log(value)
  pageParams.value.current = 1
  pageParams.value.categoryId = value
  loadPageData()
}

async function loadMore() {
  if (loading.value || noMore.value)
    return

  loading.value = true
  pageParams.value.current += 1

  useBizProductSkuApi().PageBizProductSkuWithProduct(pageParams).then(({ data, success }) => {
    if (success) {
      pageData.value.records.push(...data.records)
      pageData.value.pages = data.pages
      pageData.value.total = data.total
      if (pageParams.value.current >= data.pages) {
        noMore.value = true
      }
    }
  }).catch((err) => {
    console.log(err)
    pageParams.value.current -= 1
    console.error('加载更多失败:', err)
  }).finally(() => {
    loading.value = false
  })
}

// 跳转菜谱详情
function goToDetail(id: string | number) {
  console.log(id)
  uni.navigateTo({
    url: `/pages/sale/detail?id=${id}`,
  })
}
</script>

<template>
  <view class="container">
    <CustomNavbar title="食材购买" />
    <view class="sticky-header">
      <!-- 搜索框 -->
      <view style="padding:0 10rpx;">
        <t-search
          placeholder="搜索食材" @change="(context: any) => {
            pageParams.current = 1
            pageParams.keyword = context.value
            loadPageData()
          }"
        />
      </view>

      <!-- 分类Tabs -->
      <CategoryTabs
        v-model="activeTab"
        :tab-list="tabList"
        @change="onChange"
      />
    </view>

    <t-pull-down-refresh
      :value="enable"
      :loading-texts="['下拉刷新', '松手刷新', '正在刷新', '刷新完成']"
      :using-custom-navbar="true"
      :success-duration="600"
      @refresh="loadPageData"
      @scrolltolower="loadMore"
    >
      <!-- 空状态 -->
      <EmptyState
        v-if="pageData.records.length === 0"
        icon="🥗"
        title="暂无该分类哦～"
        description="换个分类试试吧"
      />

      <t-grid
        v-else
        :border="false"
        :column="2"
        :gutter="16"
        custom-style="padding: 16rpx;"
      >
        <t-grid-item v-for="item in pageData.records" :key="item.id">
          <ProductCard
            v-bind="item"
            style="width: 100%;"
            @click-item="goToDetail"
          />
        </t-grid-item>
      </t-grid>

      <view v-if="loading" class="load-more">
        <text class="loading-text">
          加载中...
        </text>
      </view>
      <view v-if="noMore && !loading" class="load-more">
        <text class="no-more-text">
          没有更多啦～
        </text>
      </view>

      <view style="height: 150rpx;" />
    </t-pull-down-refresh>

    <CustomTabBar />
  </view>
</template>

<style scoped>
.container {
  width: 100%;
  --td-grid-item-padding: 0;
  padding-bottom: env(safe-area-inset-bottom);
}

.sticky-header {
  position: sticky;
  top: 0;
  z-index: 998;
  background-color: white;
  border-bottom: 1rpx solid #f0f2f5;
}

/* 加载更多样式 */
.load-more {
  display: flex;
  align-items: center;
  justify-content: center;
}
.load-more .loading-text {
  font-size: 28rpx;
  color: #2d3748;
  font-weight: 500;
}
.load-more .no-more-text {
  font-size: 28rpx;
  color: #a0aec0;
}
</style>
