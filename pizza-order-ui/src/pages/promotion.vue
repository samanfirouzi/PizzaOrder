<template>
  <div class="row q-pa-md">
    <div
      class="col-3  q-pa-md items-start q-gutter-md justify-center"
      v-for="(promotion, index) in promotions"
      :key="index"
    >
      <q-card class="my-card">
        <q-img
          :src="'http://localhost:4444/' + promotion.image"
          style="max-height:165px"
          @click="routToSearch(promotion.id)"
        />
        <!-- /**sa */ -->
        <q-card-section>
          <div class="row no-wrap items-center">
            <div class="col text-h6 ellipsis">
              {{ promotion.name }}
            </div>
            <div
              class="col-auto text-green text-caption q-pt-md row no-wrap items-center"
            >
              discont: {{ promotion.discount }}%
            </div>
          </div>
        </q-card-section>

        <q-card-section class="q-pt-none">
          <q-btn
            color="green"
            glossy
            text-color="black"
            push
            @click="addOrderItem(promotion)"
          >
            Pay:
            {{
              (getPrice(promotion.promotionItems) *
                (100 - promotion.discount)) /
                100
            }}$
            <span class="text-strike	q-pl-sm">
              insted: {{ getPrice(promotion.promotionItems) }}$</span
            >
          </q-btn>
        </q-card-section>
        <q-card-section class="q-pt-none">
          <div class="text-caption text-grey ellipsis-2-lines">
            {{ promotion.description }}
          </div>
        </q-card-section>
        <q-separator />
      </q-card>
    </div>
  </div>
</template>
<script>
import orderService from "./../services/orderService";
import es from "./../services/es";

export default {
  name: "Novidades",
  data() {
    return {
      promotions: []
    };
  },
  mounted() {
    this.getPromotions();
    es.esEvent("Promotion", "Visit", null, null);
  },
  methods: {
    routToSearch(val) {
      this.$router.replace({
        name: "promotionDetail",
        params: { pId: val }
      });
    },
    getPromotions() {
      fetch("http://localhost:8080/promotion/all", {
        method: "GET"
      })
        .then(response => response.json())
        .then(data => {
          this.promotions = data;

          console.log("data : " + JSON.stringify(data));
        })
        .catch(err => {
          console.error(err);
        });
    },
    addOrderItem(p) {
      let item = {
        quantity: 1,
        promotionId: p.id,
        type: 2
      };
      orderService.addOrderItem(item, "Cart", this.$store, p.name);
    },
    getPrice(item) {
      let sumPrice = 0;
      for (let i = 0; i < item.length; i++) {
        sumPrice += item[i].product.price * item[i].quantity;
      }
      return sumPrice;
    }
  }
};
</script>
