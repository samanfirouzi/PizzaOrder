<template>
  <div class="col">
    <div class="row  justify-between">
      <div>Sum</div>
      <div>{{ sumC }} $</div>
    </div>
    <div class="row  justify-between">
      <div>Provincial Tax</div>
      <div>{{ this.$store.getters["config/getProvincialTax"] }}$</div>
    </div>
    <div class="row  justify-between">
      <div>Federal Tax</div>
      <div>{{ this.$store.getters["config/getFederalTax"] }}$</div>
    </div>
    <div class="row  justify-between">
      <div>Delivery Charges</div>
      <div>{{ this.$store.getters["config/getDelevery"] }}$</div>
    </div>
    <q-separator />
    <div class="row justify-between">
      <div>Total</div>
      <div>{{ total }}$</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "calcCost",
  props: {
    order: {
      type: Object,
      required: true
    }
  },
  computed: {
    sumC: function() {
      let sumPrice = 0;
      if (this.order !== null && this.order !== undefined) {
        let il = this.order.itemlist;
        for (let i = 0; i < il.length; i++) {
          sumPrice += il[i].price * il[i].orderItem.quantity;
        }
      }
      return sumPrice;
    },
    total: function() {
      let sum =
        this.sumC +
        this.$store.getters["config/getProvincialTax"] +
        this.$store.getters["config/getFederalTax"] +
        this.$store.getters["config/getDelevery"];

        this.$store.commit("order/lastTotal", sum);

      return sum;
    }
  }
};
</script>
