<template>
  <q-layout view="hHh lpR fFf">
    <q-header elevated :class="getHeaderColor" height-hint="98">
      <div class="row">
        <div class="col-7">
          <q-toolbar>
            <q-toolbar-title>
              <q-avatar>
                <img src="~assets/pizza.svg" />
              </q-avatar>
              Nakisa Pizza
            </q-toolbar-title>
          </q-toolbar>
          <div class="col" left>
            <q-tabs align="left" inline-label outside-arrows mobile-arrows>
              <q-route-tab to="/" icon="home" label="home" />
              <q-route-tab
                to="/promotion"
                icon="theater_comedy"
                label="Promotion"
              />
              <q-route-tab to="/lunch" icon="lunch_dining" label="Lunch" />
              <q-route-tab to="/side" icon="takeout_dining" label="Side" />
              <q-route-tab to="/drink" icon="wine_bar" label="Drink" />
            </q-tabs>
          </div>
        </div>
        <div class="col-5 self-center">
          <div class="row justify-between">
            <div class="column items-end self-end">
              <q-select
                filled
                v-model="model"
                use-input
                hide-selected
                fill-input
                input-debounce="0"
                label="Search products"
                :options="options"
                @input="routToSearch"
                @add="enterToSearch"
                @filter="filterFn" 
                @filter-abort="abortFilterFn"
                style="width: 350px"
              >
                <template v-slot:no-option>
                  <q-item>
                    <q-item-section class="text-grey">
                      No results
                    </q-item-section>
                  </q-item>
                </template>
              </q-select>
            </div>
            <div class="column items-end q-px-sm self-center">
              <div class="row">
                <q-btn
                  class=" q-ma-sm "
                  color="blue-8"
                  round
                  v-if="this.$store.getters['user/getAccessToken'] === ''"
                  @click="changeSginupVisibility(!isSPVisible)"
                >
                  <q-icon name="person_add" size="sm" color="lime-1" />
                </q-btn>

                <q-chip
                  class=" q-pa-lg "
                  v-if="this.$store.getters['user/getAccessToken'] === ''"
                >
                  <div class="q-px-sm">
                    Guest
                  </div>
                  <q-btn
                    color="lime-9"
                    round
                    @click="changeLoginVisibility(!isLPVisible)"
                  >
                    <q-icon name="login" size="sm" color="lime-1" />
                  </q-btn>
                </q-chip>
              </div>

              <q-chip
                class="q-pa-lg "
                v-if="this.$store.getters['user/getAccessToken'] !== ''"
              >
                <div class="q-px-sm">
                  {{ this.$store.getters["user/getUserInfo"] }}
                </div>
                <q-btn color="red-8" round @click="logoutUser">
                  <q-icon name="logout" size="sm" color="lime-1" />
                </q-btn>
              </q-chip>
            </div>
          </div>
        </div>
      </div>
    </q-header>

    <q-drawer
      v-model="orderBar"
      side="right"
      bordered
      class="bg-orange-8"
      :width="385"
    >
      <order-left />
    </q-drawer>

    <q-page-container   style="height: 100%;">
      <router-view />
    </q-page-container>
    <login-modal />
    <signup-modal />

    <q-page-sticky v-if="isAdmin" position="bottom-right" :offset="[18, 18]">
      <q-fab
        v-model="fabLeft"
        vertical-actions-align="right"
        label="Management"
        color="primary"
        glossy
        icon="keyboard_arrow_up"
        direction="up"
      >
        <q-fab-action
          label-position="left"
          color="primary"
          to="/promotionManagement"
          icon="theater_comedy"
          label="Promotion"
        />
        <q-fab-action
          label-position="left"
          color="secondary"
          to="/productManagement"
          icon="lunch_dining"
          label="Product"
        />
        <q-fab-action
          label-position="left"
          color="orange"
          to="/orderManagement"
          icon="shop"
          label="Orders"
        />        
        <q-fab-action
          label-position="left"
          color="green"
          to="/configManagement"
          icon="settings"
          label="Config"
        />
      </q-fab>
    </q-page-sticky>
  </q-layout>
</template>
<script>
//import { mapActions } from "veux";
import LoginModal from "src/components/LoginModal.vue";
import OrderDetail from "src/components/orderDetail.vue";
import OrderLeft from "src/components/orderLeft.vue";
import SignupModal from "src/components/signupModal.vue";
export default {
  components: { LoginModal, OrderDetail, OrderLeft, SignupModal },
  data() {
    return {
      fabLeft: false,
      model: null,
      options: []
    };
  },
  computed: {
    isAdmin: {
      get() {
        if (this.$store.getters["user/getPermisions"].length < 1) return false;
        else if (
          this.$store.getters["user/getPermisions"][0].authority === "ADMIN"
        )
          return true;
        return false;
      }
    },
    getBKColor: {
      get() {
        if (this.$store.getters["user/getPermisions"].length < 1)
          return "bg-grey-11 text-white";
        else if (
          this.$store.getters["user/getPermisions"][0].authority === "ADMIN"
        )
          return "bg-blue-1 text-white";
        return "bg-amber-1 text-white";
      }
    },
    getHeaderColor: {
      get() {
        if (this.$store.getters["user/getPermisions"].length < 1)
          return "bg-yellow-8 text-white";
        else if (
          this.$store.getters["user/getPermisions"][0].authority === "ADMIN"
        )
          return "bg-blue-9 text-white";
        return "bg-amber-9 text-white";
      }
    },
    isLPVisible: {
      get() {
        return this.$store.getters["user/isLoginPageVisible"];
      },
      set(val) {
        this.$store.commit("user/changeLoginFormVisibility", val);
      }
    },
    isSPVisible: {
      get() {
        return this.$store.getters["user/isSignupPageVisible"];
      },
      set(val) {
        this.$store.commit("user/changeSignupFormVisibility", val);
      }
    },
    orderBar: {
      get() {
        console.log(
          "befor calc: " +
            JSON.stringify(this.$store.getters["order/getActiveOrder"])
        );
        return (
          this.$store.getters["order/getActiveOrder"] !== null &&
          Object.keys(this.$store.getters["order/getActiveOrder"]).length > 0
        );
        // return this.$store.getters["order/getActiveOrder"] !== {};
      }
    }
  },
  methods: {
    routToSearch(val) {
      console.log("routToSearch: " + val.value);

      this.$router.replace({
        name: "productDetail",
        params: { pId: val.value }
      });
    },
    enterToSearch(val) {
      console.log("enterToSearch: " + val.value);
    },
    filterFn(val, update, abort) {
      // call abort() at any time if you can't retrieve data somehow
      if (val === null || val === "") abort();
      update(() => {
        fetch(
          "http://localhost:8080/product/search?" +
            "pageNo=" +
            0 +
            "&pageSize=" +
            10 +
            "&name=" +
            val,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              "Access-Control-Allow-Origin": "http://localhost:8080/*",
              "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE"
            }
          }
        )
          .then(response => response.json())
          .then(data => {
            let content = data.content;
            this.options = [];
            for (let i = 0; i < content.length; i++) {
              this.options[i] = {
                label: content[i].name,
                value: content[i].id
              };
            }
          })
          .catch(err => {
            this.prr = null;
            console.error(err);
          });
      });
    },

    abortFilterFn() {
      // console.log('delayed filter aborted')
    },
    changeLoginVisibility(val) {
      this.$store.commit("user/changeLoginFormVisibility", val);
    },
    changeSginupVisibility(val) {
      this.$store.commit("user/changeSignupFormVisibility", val);
    },
    logoutUser() {
      /* fetch("http://localhost:8080" + "/oauth/logout?refresh="+this.$store.getters["getRefreshToken"] , {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE",
          Authorization: "bearer " + this.$store.getters["user/getAccessToken"]
        }
      })
        .then(response => response.json())
        .then(data => {
        })
        .catch(err => {
          console.error(err);
        }); */

      this.$store.commit("user/saveToken", "");
      this.$store.commit("user/saveRefreshToken", "");
      this.$store.commit("user/saveUsername", "");
      this.$store.commit("order/setActiveOrder", null);
      this.$store.commit("user/setPermisions", []);

       this.$router.replace({
        path: "/"
      });
    }
  }
};
</script>
