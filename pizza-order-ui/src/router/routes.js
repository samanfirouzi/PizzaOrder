
const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/Index.vue') }
    ]
  },
  {
    path: '/promotion',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/promotion.vue') }
    ]
  },
  {
    path: '/lunch',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/lunch.vue') }
    ]
  },
  {
    path: '/drink',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/drink.vue') }
    ]
  },
  {
    path: '/side',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/side.vue') }
    ]
  },
  {
    path: '/productManagement',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/productManagement.vue') }
    ]
  },
  {
    path: '/productDetail/:pId',
    component: () => import('layouts/MainLayout.vue'),
    // props: (route) => {pId: route.params.pId},
    children: [
      { path: '',name: 'productDetail', props: true, component: () => import('pages/productDetail.vue') }
    ]
  },
  {
    path: '/promotionDetail/:pId',
    component: () => import('layouts/MainLayout.vue'),
    // props: (route) => {pId: route.params.pId},
    children: [
      { path: '',name: 'promotionDetail', props: true, component: () => import('pages/promotionDetail.vue') }
    ]
  },
  {
    path: '/promotionManagement',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/promotionManagement.vue') }
    ]
  },
  {
    path: '/orderManagement',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/orderManagement.vue') }
    ]
  },
  {
    path: '/configManagement',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/configManagement.vue') }
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '*',
    component: () => import('pages/Error404.vue')
  }
]

export default routes
