<template>
  <div
    class="q-pa-sm bg-orange-1 "
    v-if="getOrderVuex() !== null && JSON.stringify(getOrderVuex()) !== '{}'"
  >
    <div class="q-gutter-y-md">
      <q-card>
        <q-tabs
          v-model="tab"
          dense
          class="bg-yellow-8 text-grey-1"
          active-color="primary"
          indicator-color="primary"
          align="justify"
          narrow-indicator
        >
          <q-tab name="active" label="Cart" />
          <q-tab name="favorite" label="Favorite" />
          <q-tab name="saved" label="Inactive" />
          <q-tab name="history" label="history" />
        </q-tabs>

        <q-separator />

        <q-tab-panels
          v-model="tab"
          v-if="getOrderVuex() !== undefined"
          animated
        >
          <q-tab-panel
            name="active"
            v-if="getOrderVuex().current !== undefined"
          >
            <order-detail
              v-for="(oItem, index) in getOrderVuex().current.itemlist"
              :orderItem="oItem"
              :key="index"
              destination="Cart"
            />
          </q-tab-panel>

          <q-tab-panel name="favorite" v-if="getOrderVuex().favorite !== null">
            <div v-for="(fav, indexF) in getOrderVuex().favorite" :key="indexF">
              <div class=" bg-amber-11 q-pa-xs rounded-borders">
                <order-detail
                  v-for="(oItem, index) in fav.itemlist"
                  :orderItem="oItem"
                  :key="index"
                  destination="Favorite"
                  :changable="false"
                />
                <q-btn
                  class="q-ma-sm"
                  icon="shopping_bag"
                  color="green"
                  label="I want"
                  @click="confirmReplaceFavorite(fav)"
                />
                <q-btn
                  class="q-ma-sm"
                  icon="delete"
                  color="red"
                  label="Delete"
                  @click="confirmDeleteFavorit(fav)"
                />
              </div>
              <q-separator class="q-ma-sm " />
            </div>
          </q-tab-panel>

          <q-tab-panel name="saved" v-if="getOrderVuex().saved !== undefined">
            <order-detail
              v-for="(oItem, index) in getOrderVuex().saved.itemlist"
              :orderItem="oItem"
              :key="index"
              destination="Inactive"
            />
          </q-tab-panel>
       
          <q-tab-panel name="history" v-if="getOrderVuex().history !== null">
            <div v-for="(fav, indexF) in getOrderVuex().history" :key="indexF">
              <div class=" bg-amber-11 q-pa-xs rounded-borders">
                <order-detail
                  v-for="(oItem, index) in fav.itemlist"
                  :orderItem="oItem"
                  :key="index"
                  destination="Favorite"
                  :changable="false"
                />
                <q-btn
                  class="q-ma-sm"
                  icon="shopping_bag"
                  color="green"
                  label="I want it again"
                  @click="confirmReplaceHistory(fav)"
                />
              </div>
              <q-separator class="q-ma-sm " />
            </div>
          </q-tab-panel>
 </q-tab-panels>
      </q-card>

      <q-card>
        <q-tab-panels v-model="tab" animated>
          <q-tab-panel
            name="active"
            v-if="getOrderVuex().current !== undefined"
          >
            <calc-cost :order="getOrderVuex().current" />
          </q-tab-panel>

          <q-tab-panel name="saved" v-if="getOrderVuex().saved !== undefined">
            <calc-cost :order="getOrderVuex().saved" />
          </q-tab-panel>
        </q-tab-panels>
      </q-card>

      <q-card>
        <q-tab-panels v-model="tab" animated>
          <q-tab-panel name="active">
            <q-btn @click="payIt" class="bg-green q-mx-sm">pay it</q-btn>
            <q-btn @click="doFavorite" class="bg-orange ">Favorite</q-btn>
            <q-btn @click="saveForLater" class="bg-red q-mx-sm"
              >inactive
            </q-btn>
          </q-tab-panel>

          <q-tab-panel name="saved">
            <q-btn @click="activateSaved" class="bg-green">Activate</q-btn>
          </q-tab-panel>
        </q-tab-panels>
      </q-card>
    </div>
  </div>
</template>

<script>
import CalcCost from "./calcCost.vue";
import orderDetail from "./orderDetail.vue";
import es from "./../services/es";
import orderService from "./../services/orderService";

export default {
  components: { orderDetail, CalcCost },
  data() {
    return {
      tab: "active"
    };
  },

  methods: {
    confirmReplaceFavorite(f) {
      this.$q
        .dialog({
          title: "Confirm",
          message:
            "Would you like to replace the favotite with your cart list? ",
          cancel: true,
          persistent: true
        })
        .onOk(() => {
          orderService.activeFavorite(f.id, this.$store);
        });
    },    
    confirmReplaceHistory(f) {
      this.$q
        .dialog({
          title: "Confirm",
          message:
            "Would you like to replace the History with your cart list? ",
          cancel: true,
          persistent: true
        })
        .onOk(() => {
          orderService.activeHistory(f.id, this.$store);
        });
    },
    confirmDeleteFavorit(f) {
      this.$q
        .dialog({
          title: "Confirm",
          message: "Would you like to delete the favotite? ",
          cancel: true,
          persistent: true
        })
        .onOk(() => {
          orderService.deleteFavorite(f.id, this.$store);
        });
    },
    getOrderVuex() {
      return this.$store.getters["order/getActiveOrder"];
    },
    saveForLater() {
      orderService.saveForLater(this.$store);
    },
    activateSaved() {
      orderService.activateSaved(this.$store);
    },
    doFavorite() {
      orderService.addFavorite(this.$store);
    },
    payIt() {
      orderService.payIT(this.$store);
      es.buyEvent(
        this.$store.getters["user/getUserInfo"],
        this.$store.getters["order/lastTotal"],
        this.getOrderVuex().current.id
      );
    }
  }
};
</script>
