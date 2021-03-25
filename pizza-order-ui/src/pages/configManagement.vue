<template>
  <div class=" bg-blue text-white text-center q-pa-md  col" v-if="isAdmin">
    <div
      class=" rounded-borders q-ma-md q-pa-md shadow-4 bg-yellow-1 text-grey-10"
    >
      <div class="row">
        <div class="col q-ma-md">
          <q-input label="config Name" clearable outlined v-model="pName" />
        </div>
        <div class="col q-ma-md">
          <q-input label="config value" clearable outlined v-model="pValue" />
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
        <q-btn
          class=" q-ma-sm"
          icon="add"
          color="green"
          label="Add new"
          @click="addItem()"
        ></q-btn>
      </div>
    </div>
    <q-separator />
    <div class=" q-ma-md rounded-borders shadow-4 bg-yellow-1 text-grey-10">
      <div class="row q-pa-sm bg-orange-4  ">
        <div class="col">Id</div>
        <div class="col">Name</div>
        <div class="col">value</div>
      </div>
      <q-separator class="q-pa-xs bg-red-7" />
      <div
        v-for="(a, index) in prr.content"
        :key="index"
        :class="getBgColor(index)"
      >
        <div class="row q-pa-sm shadow-up-1">
          <div class="col">{{ a.id }}</div>
          <div class="col">{{ a.key }}</div>
          <div class="col">{{ a.value }}</div>
          <div class="col">
            <q-btn
              icon="create"
              label="Edit"
              class="q-mr-sm"
              color="orange"
              size="10px"
              @click="editItem(a)"
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
          <div class="text-h6">{{ editModalType }} config</div>
        </q-card-section>

        <q-card-section class="q-pt-none q-pa-md">
          <div class="row">
            <div class="col q-pa-md">
              <q-input
                label="config Name"
                clearable
                outlined
                v-model="selectedItem.key"
              />
            </div>
            <div class="col q-pa-md">
              <q-input
                label="config value"
                clearable
                outlined
                v-model="selectedItem.value"
              />
            </div>
          </div>
        </q-card-section>

        <q-card-actions align="right" class="bg-orange text-white ">
          <q-btn flat label="Cancel" v-close-popup />
          <q-btn flat :label="editModalType" @click="editOrAddConfig" />
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
      pName: "",
      pValue: ""
    };
  },
  methods: {
    addItem() {
      this.selectedItem = {
        id: null,
        key: null,
        value: null
      };

      this.editModal = true;
      this.editModalType = "Add";
    },
    editItem(a) {
      this.selectedItem = JSON.parse(JSON.stringify(a));

      this.editModal = true;
      this.editModalType = "Edit";
    },
    search(pageNum) {
      fetch(
        "http://localhost:8080/config/search?" +
          "pageNo=" +
          pageNum +
          "&pageSize=" +
          5 +
          "&name=" +
          this.pName +
          "&value=" +
          this.pValue,
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
        .then(response => response.json())
        .then(data => {
          this.prr = data;
        })
        .catch(err => {
          this.prr = null;
          console.error(err);
        });
    },
    editOrAddConfig() {
      let s = this.selectedItem;
      let obj = {
        id: s.id,
        value: s.value,
        key: s.key
      };
      console.log(JSON.stringify(obj));

      fetch("http://localhost:8080" + "/config/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "http://localhost:8080/*",
          "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE",
          Authorization: "bearer " + this.$store.getters["user/getAccessToken"]
        },
        body: JSON.stringify(obj)
      })
        .then(response => response.json())
        .then(data => {
          this.editModal = false;
          this.search(0);
        })
        .catch(err => {
          console.error(err);
        });
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
