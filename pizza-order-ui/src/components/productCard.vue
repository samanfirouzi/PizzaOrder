<template>
  <q-card class="my-card">
    <q-img
      :src="'http://localhost:4444/'+product.image"
      @click="routToSearch(product.id)"
      style="max-height:170px"
    />
    <q-card-section>
      <div class="row no-wrap items-center">
        <div class="col text-grey-10 text-h6 ellipsis">
          {{ product.name }}
        </div>
        <div
          class="col-auto text-green text-caption q-pt-md row no-wrap items-center"
        >
          new
        </div>
      </div>
    </q-card-section>

    <q-card-section class="q-pt-none">
      <q-btn-group push>
        <q-btn
          :color="'yellow-' + (3 * index + 4)"
          glossy
          size="11px"
          text-color="black"
          push
          @click="addOrderItem(pInfo.id)"
          v-for="(pInfo, index) in product.products"
          :key="index"
          >{{ getSize(pInfo.size) }}: ${{ pInfo.price }}</q-btn
        >
      </q-btn-group>
    </q-card-section>
    <q-card-section class="q-pt-none">
      <div class="text-caption text-grey ellipsis-2-lines">
        {{ product.description }}
      </div>
    </q-card-section>
    <q-separator />
  </q-card>
</template>

<script>
import orderService from "./../services/orderService";
import utils from "./../services/util";
export default {
  name: "calcCost",
  props: {
    product: {
      type: Object,
      required: true
    }
  },
  mounted() {},
  computed: {},
  methods: {
    getSize(val) {
      return utils.getSizeName(val);
    },
    routToSearch(val) {
      this.$router.replace({
        name: "productDetail",
        params: { pId: val }
      });
    },

    addOrderItem(pId) {
      let item = {
        quantity: 1,
        productId: pId,
        type: 1
      };
      orderService.addOrderItem(item, "Cart", this.$store,this.product.name);
    }
  }
};
</script>
