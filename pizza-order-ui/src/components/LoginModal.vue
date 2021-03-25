<template>
  <q-dialog
    v-model="isLPVisible"
    persistent
    transition-show="flip-down"
    transition-hide="flip-up"
  >
    <q-card class="bg-lime-9 text-white">
      <q-bar>
        <q-space />
        <q-btn dense flat icon="close" v-close-popup>
          <q-tooltip content-class="bg-white text-primary">Close</q-tooltip>
        </q-btn>
      </q-bar>

      <q-card-section>
        <div class="text-h6">Login</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        Hello dear customer, you can access to ordering after login.
      </q-card-section>
      <q-card-section class="q-pt-none">
        <q-input
          ref="username"
          filled
          v-model="username"
          label="Your username *"
          hint="Fill your UserName"
          lazy-rules
          :rules="[val => (val && val.length > 0) || 'Please type something']"
        />
        <q-separator class="q-mt-lg" />
        <q-input
          v-model="password"
          filled
          :type="isPwd ? 'password' : 'text'"
          hint="Enter your Password carefully "
        >
          <template v-slot:append>
            <q-icon
              :name="isPwd ? 'visibility_off' : 'visibility'"
              class="cursor-pointer"
              @click="isPwd = !isPwd"
            />
          </template>
        </q-input>
      </q-card-section>
      <q-card-section>
        <q-btn-group push>
          <q-btn
            color="red"
            glossy
            v-close-popup
            text-color="black"
            push
            @click="closeModal"
            label="Cancel"
            icon="verified_user"
          />
          <q-btn
            color="primary"
            @click="getToken"
            glossy
            text-color="black"
            push
            label="Login"
          />
        </q-btn-group>
      </q-card-section>
    </q-card>
  </q-dialog>
</template>

<script>
import es from "./../services/es";
import utils from "./../services/util";
export default {
  computed: {
    isLPVisible: {
      get() {
        return this.$store.getters["user/isLoginPageVisible"];
      },
      set(val) {
        this.$store.commit("user/changeLoginFormVisibility", val);
      }
    }
  },
  data() {
    return {
      name: "",
      username: "",
      password: "",
      isPwd: true,
      visible: false
    };
  },
  methods: {
    getToken() {
      fetch(
        "http://localhost:8080" +
          "/oauth/token?grant_type=password&username=" +
          this.username +
          "&password=" +
          this.password,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            "Access-Control-Allow-Origin": "http://localhost:8080/*",
            "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE",
            authorization: "Basic c2FtYW46c2FtYW4tc2VjdXJlLWtleQ=="
          }
        }
      )
        .then(response => response.json())
        .then(data => {
          console.log(JSON.stringify(data));
          if (data.error === "invalid_grant") {
            utils.notify("Login failed, Username or Password is incorrect");
          } else {
            this.$store.commit("user/saveToken", data.access_token);
            this.$store.commit("user/saveRefreshToken", data.refresh_token);
            this.$store.commit("user/saveUsername", this.username);
            this.fetchOrders();
            this.configs();
            this.fetchUserGrant();
            es.esEvent("Login", "getToken", this.username, this.username);
            this.closeModal();
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    fetchOrders() {
      fetch("http://localhost:8080" + "/order/orderData", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "http://localhost:8080/*",
          "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE",
          Authorization: "bearer " + this.$store.getters["user/getAccessToken"]
        }
      })
        .then(response => response.json())
        .then(data => {
          this.$store.commit("order/setActiveOrder", data);
        })
        .catch(err => {
          console.error(err);
        });
    },
    configs() {
      this.doConfig("FederalTax");
      this.doConfig("ProvincialTax");
      this.doConfig("Delevery");
      this.doConfig("MinOrder");
      this.doConfig("MaxItem");
    },
    doConfig(keyName) {
      console.log(keyName + ": ");
      fetch("http://localhost:8080" + "/config/get" + keyName, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE",
          Authorization: "bearer " + this.$store.getters["user/getAccessToken"]
        }
      })
        .then(response => response.json())
        .then(data => {
          console.log(keyName + ": " + data);
          this.$store.commit("config/set" + keyName, data);
        })
        .catch(err => {
          console.error(err);
        });
    },
    fetchUserGrant() {
      fetch("http://localhost:8080" + "/userGrants", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE",
          Authorization: "bearer " + this.$store.getters["user/getAccessToken"]
        }
      })
        .then(response => response.json())
        .then(data => {
          console.log("grant: " + data);
          this.$store.commit("user/setPermisions", data);
        })
        .catch(err => {
          console.error(err);
        });
    },
    onItemClick(pInfo) {
      console.log("Clicked on an Item: " + JSON.stringify(pInfo));
    },
    closeModal() {
      this.username = "";
      this.password = "";
      this.isLPVisible = false;
    },
    getName(size) {
      if (size === "M") return "Medium";
      if (size === "S") return "Small";
      if (size === "L") return "Large";
    }
  }
};
</script>
