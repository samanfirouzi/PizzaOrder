<template>
  <q-infinite-scroll>
    <div class="q-my-md q-pa-xs bg-brown-1 shadow-2 rounded-borders	">
      <div class="row">
        <q-img
          @click=""
          :src="'http://localhost:4444/' + orderItem.image"
          :ratio="1"
          style="max-height: 50px;"
        />
        <div class="col q-ma-xs">
          <div class="row text-blue-14  justify-between">
            <q-btn
              flat
              size="14px"
              padding="none"
              style="color: #0000FF"
              :label="orderItem.name"
            />
          </div>
          <div class="row text-green-8  justify-between">
            <div>{{ getSizeName(orderItem.size) }}</div>
            <div>{{ orderItem.price }}$</div>
          </div>
          <div class="row text-grey-9  justify-between">
            <div class="row ">
              <div class="q-mr-sm">
                Quantity: {{ orderItem.orderItem.quantity }}
              </div>
              <q-btn
                v-if="changable"
                icon="add"
                class="q-mr-xs"
                round
                color="primary"
                size="6px"
                @click="addOrderItem(orderItem.orderItem)"
              ></q-btn>
              <q-btn
                icon="remove"
                v-if="changable"
                round
                color="red"
                size="6px"
                @click="removeOrderItem(orderItem.orderItem)"
              ></q-btn>
            </div>
            <div>{{ orderItem.price * orderItem.orderItem.quantity }}$</div>
          </div>
        </div>
      </div>
    </div>
  </q-infinite-scroll>
</template>

<script>
import utils from "./../services/util";
import orderService from "./../services/orderService";
export default {
  name: "orderDetail",
  props: {
    orderItem: {
      type: Object,
      required: true
    },
    destination: {
      type: String,
      required: true
    },
    changable: {
      type: Boolean,
      required: false,
      default: true
    }
  },
  methods: {
    routToSearch(val) {
      console.log(JSON.stringify(val));

      let rName = "";
      if (val.orderItem.promotionId === null) rName = "productDetail";
      else rName = "promotionDetail";
      this.$router.replace({
        name: rName,
        params: { pId: val.orderItem.productId }
      });
    },
    addOrderItem(item) {
      orderService.addOrderItem(
        item,
        this.destination,
        this.$store,
        this.orderItem.name
      );
    },
    removeOrderItem(item) {
      orderService.removeOrderItem(item, this.$store);
    },
    getSizeName(size) {
      return utils.getSizeName(size);
    }
  }
};
</script>
