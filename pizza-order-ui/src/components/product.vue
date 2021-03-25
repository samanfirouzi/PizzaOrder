<template>
  <div class="row q-pa-md">
    <div
      class="col-3  q-pa-md items-start q-gutter-md justify-center"
      v-for="(product, index) in products"
      :key="index"
    >
      <product-card :product="product"></product-card>
    </div>
  </div>
</template>
<script>
import productService from "../services/productService";
import es from "../services/es";
import utils from "../services/util";
import productCard from "src/components/productCard.vue";
export default {
  components: { productCard },
  name: "Novidades",
  props: {
    productType: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      products: []
    };
  },
  mounted() {
    productService.getAllProductsByType(this.productType, this.callBackSearch);
    es.esEvent(utils.getProductTypeName(this.productType), "Visit", null, null);
  },
  methods: {
    callBackSearch(data) {
      this.products = data;
    }
  }
};
</script>
