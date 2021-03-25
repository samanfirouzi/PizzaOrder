<template>
  <div class=" bg-blue text-white text-center q-pa-md  col" v-if="isAdmin">
    <div
      class=" rounded-borders q-ma-md q-pa-md shadow-4 bg-yellow-1 text-grey-10"
    >
      <div class="row">
        <div class="col q-ma-md">
          <q-input label="Order id" clearable outlined v-model="orderIdO" />
        </div>
        <div class="col q-ma-md">
          <q-select
            label="Order status"
            :options="orderOptions"
            outlined
            v-model="statusO"
          />
        </div>
      </div>
      <div class="row">
        <q-btn
          class=" q-ma-sm"
          icon="search"
          color="blue"
          label="Search"
          @click="search(0)"
        ></q-btn>
      </div>
    </div>
    <q-separator />
    <div class=" q-ma-md rounded-borders shadow-4 bg-yellow-1 text-grey-10">
      <div class="row q-pa-sm bg-orange-4  ">
        <div class="col">Id</div>
        <div class="col">userId</div>
        <div class="col">date</div>
        <div class="col">status</div>
        <div class="col">actions</div>
      </div>
      <q-separator class="q-pa-xs bg-red-7" />
      <div
        v-for="(a, index) in prr.content"
        :key="index"
        :class="getBgColor(index)"
      >
        <div class="row q-pa-sm shadow-up-1">
          <div class="col">{{ a.id }}</div>
          <div class="col">{{ a.userId }}</div>
          <div class="col">{{ a.date }}</div>
          <div class="col">{{ getStatusName(a.state) }}</div>
          <div class="col">
            <q-btn
              icon="info"
              label="detail"
              class="q-mr-sm"
              color="orange"
              size="10px"
              @click="detailMode(a)"
            />
          </div>
        </div>
      </div>
      <q-separator class="bg-red-7" />
      <div class="q-pa-xs flex flex-center">
        <q-pagination v-model="pageIndex" :max="pageAva()"></q-pagination>
      </div>
    </div>

    <q-dialog v-model="editModal" persistent>
      <q-card
        v-if="selectedItem !== null"
        style="width: 900px; max-width: 80vw;"
      >
        <q-card-section class="bg-orange text-grey-1">
          <div class="text-h6">detail of order</div>
        </q-card-section>

        <q-card-section class="q-pt-none q-pa-md">
          <div
            :class="getBgColor(index)"
            class="row"
            v-for="(item, index) in selectedItem.orderItems"
          >
            <div class="col q-pa-md">
              Item type: {{ item.type === 1 ? "Product" : "Promotion" }}
            </div>
            <div class="col q-pa-md">Quantity: {{ item.quantity }}</div>
            <div class="col q-pa-md" v-if="item.productId !== null">
              Product id: {{ item.productId }}
            </div>
            <div class="col q-pa-md" v-if="item.promotionId !== null">
              Promotion id: {{ item.promotionId }}
            </div>
          </div>
        </q-card-section>

        <q-card-actions align="right" class="bg-orange text-white ">
          <q-btn flat label="Cancel" v-close-popup />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
  <expired v-else-if="!isAdmin" />
</template>
<script>
import expired from "src/components/expired.vue";
export default {
  components: { expired },
  data() {
    return {
      selectedItem: null,
      editModal: false,
      editModalType: "",
      prr: {},
      orderIdO: null,
      statusO: null,
      orderOptions: [
        {
          label: "",
          value: null
        },
        {
          label: "Active",
          value: 1
        },
        {
          label: "Inactive",
          value: 2
        },
        {
          label: "Lunched",
          value: 3
        },
        {
          label: "Favorite",
          value: 4
        }
      ]
    };
  },
  methods: {
    detailMode(a) {
      this.selectedItem = a;
      this.editModal = true;
    },
    getStatusName(s) {
      if (s === 1) return "Active";
      if (s === 2) return "Inactive";
      if (s === 3) return "Lunched";
      if (s === 4) return "Favorite";
    },
    search(pageNum) {
      fetch(
        "http://localhost:8080/order/search?" +
          "pageNo=" +
          pageNum +
          "&pageSize=5" +
          (this.orderIdO === null ? "" : "&orderId=" + this.orderIdO) +
          (this.statusO === null || this.statusO.value === null
            ? ""
            : "&status=" + this.statusO.value),
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "http://localhost:8080/*",
            "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE",
            Authorization:
              "bearer " + this.$store.getters["user/getAccessToken"]
          }
        }
      )
        .then(response => {
          return response.json();
          // console.log("err str: " + JSON.stringify(response.json()));
          // if (response.ok) return response.json();
          // else this.catchError(response);
        })
        .then(data => {
          if (data.error === undefined) this.prr = data;
          else {
            this.catchError(data);
          }
        })
        .catch(err => {
          console.error("err : " + err);
          this.prr = null;
        });
    },
    catchError(res) {
      if (res.error_description.includes("Access token expired")) this.login();
    },
    getBgColor(val) {
      if (val % 2 === 0) return "bg-blue-1";
      // return 'bg-yellow-1'
    },
    pageAva() {
      console.log("totalPages:" + this.prr.totalPages);
      if (this.prr.totalPages === NaN || this.prr.totalPages === undefined)
        return 0;
      else return this.prr.totalPages;
    }
  },
  computed: {
    pageIndex: {
      get() {
        return this.prr.number + 1;
      },
      set(val) {
        this.search(val - 1);
      }
    },
    isAdmin: {
      get() {
        if (this.$store.getters["user/getPermisions"].length < 1) return false;
        else if (
          this.$store.getters["user/getPermisions"][0].authority === "ADMIN"
        )
          return true;
        return false;
      }
    }
  },
  mounted() {
    this.search(0);
  }
};
</script>
