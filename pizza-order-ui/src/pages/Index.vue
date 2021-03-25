<template>
  <div class=" bg-orange-1">
    <q-parallax :height="450" class="shadow-4">
      <template v-slot:media>
        <video height="1100" poster="http://localhost:4444/images/upload_cc6fcd8cffda0b800f9d2cb742527f4d.jpg" autoplay loop muted>
          <source type="video/mp4" src="http://localhost:4444/videoplayback2.mp4">
        </video>
      </template>
    </q-parallax>
    <div class="col-auto">
      <div class="q-pa-sm row items-start  justify-center">
        <q-intersection
          v-for="(product, index) in products"
          :key="index"
          transition="scale"
          class="col-3  q-pa-sm items-start q-gutter-md justify-center"
        >
          <product-card :product="product"></product-card>
        </q-intersection>
      </div>
    </div>
  </div>
</template>

<script>
import productCard from "src/components/productCard.vue";
import productService from "./../services/productService";
import es from "./../services/es";
export default {
  components: { productCard },
  data() {
    return {
      slide: 1,
      stars: 4,
      products: [],
      autoplay: true
    };
  },
  mounted() {
    productService.getAllProducts(this.callBackSearch);
    es.esEvent("Home", "Visit", null, null);
  },
  methods: {
    callBackSearch(data) {
      this.products = data;
    }
  }
};
</script>
