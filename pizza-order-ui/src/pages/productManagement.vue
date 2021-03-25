<template>
  <div class=" bg-blue text-white text-center q-pa-md  col" v-if="isAdmin">
    <div
      class=" rounded-borders q-ma-md q-pa-md shadow-4 bg-yellow-1 text-grey-10"
    >
      <div class="row">
        <div class="col q-ma-md">
          <q-input
            type="number"
            label="Product ID"
            clearable
            outlined
            v-model="pId"
          />
        </div>
        <div class="col q-ma-md">
          <q-input label="Product Name" clearable outlined v-model="pName" />
        </div>
        <div class="col q-ma-md">
          <q-select
            label="Product type"
            :options="options"
            outlined
            v-model="pType"
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
        <div class="col">Image</div>
        <div class="col">type</div>
        <div class="col">prices</div>
        <div class="col">operation</div>
      </div>
      <q-separator class="q-pa-xs bg-red-7" />
      <div
        v-for="(a, index) in prr.content"
        :key="index"
        :class="getBgColor(index)"
      >
        <div class="row q-pa-sm shadow-up-1">
          <div class="col">{{ a.id }}</div>
          <div class="col">{{ a.name }}</div>
          <div class="col">
            <q-img
              :src="'http://localhost:4444/' + a.image"
              style="max-height:25px"
            >
              <q-tooltip
                content-class="bg-red"
                content-style="font-size: 16px"
                :offset="[10, 10]"
              >
                {{ a.image }}
              </q-tooltip>
            </q-img>
          </div>
          <div class="col">{{ getProductTypeName(a.type) }}</div>
          <div class="col">
            <div class=" row  justify-end">
              <div class="col" v-for="(p, i) in a.products">
                {{ p.size }}: {{ p.price }}$
              </div>
            </div>
          </div>
          <div class="col">
            <q-btn
              icon="create"
              label="Edit"
              class="q-mr-sm"
              color="orange"
              size="10px"
              @click="editItem(a)"
            />
            <q-btn
              icon="no_meals"
              label="Deactivate"
              @click="confirm(a)"
              color="red"
              size="10px"
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
          <div class="text-h6">{{ editModalType }} Item</div>
        </q-card-section>

        <q-card-section class="q-pt-none q-pa-md">
          <div class="row">
            <div class="col-8 ">
              <div class=" q-pa-md">
                <q-input
                  label="Product Name"
                  clearable
                  outlined
                  v-model="selectedItem.name"
                />
              </div>
              <div class=" q-pa-md">
                <q-select
                  label="Product type"
                  :options="options"
                  outlined
                  v-model="selectedProductTypeModal"
                />
              </div>
              <div class=" q-pa-md">
                <q-input
                  label="Product description"
                  clearable
                  outlined
                  v-model="selectedItem.description"
                />
              </div>
            </div>
            <div class="col-4">
              <div class="row q-pa-md">
                <q-uploader
                  url="http://localhost:4444/upload"
                  @added="addedImg"
                  @uploaded="fileUploaded"
                  color="orange"
                />
              </div>
            </div>
          </div>

          <div class="row">
            <div class="col q-pa-md">
              <q-checkbox label="Small Size" v-model="ss.exist" />
            </div>
            <div class="col q-pa-md">
              <q-checkbox label="Medium Size" v-model="ms.exist" />
            </div>
            <div class="col q-pa-md">
              <q-checkbox label="Large Size" v-model="ls.exist" />
            </div>
          </div>
          <div class="row">
            <div class="col q-pa-md">
              <q-input
                type="number"
                label="Small Size Price"
                clearable
                outlined
                v-model="ss.price"
              />
            </div>
            <div class="col q-pa-md">
              <q-input
                type="number"
                label="Medium Size Price"
                clearable
                outlined
                v-model="ms.price"
              />
            </div>
            <div class="col q-pa-md">
              <q-input
                type="number"
                label="Large Size Price"
                clearable
                outlined
                v-model="ls.price"
              />
            </div>
          </div>
          <div class="row"></div>
        </q-card-section>

        <q-card-actions align="right" class="bg-orange text-white ">
          <q-btn flat label="Cancel" v-close-popup />
          <q-btn flat :label="editModalType" @click="editOrAddProduct" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
  <expired v-else-if="!isAdmin" />
</template>
<script>
import expired from "src/components/expired.vue";
import utils from "./../services/util";

export default {
  components: { expired },
  data() {
    return {
      selectedItem: null,
      editModal: false,
      editModalType: "",
      ss: { exist: false, price: 0 },
      ms: { exist: false, price: 0 },
      ls: { exist: false, price: 0 },
      prr: {},
      pId: "",
      pName: "",
      pType: {
        label: "",
        value: ""
      },
      options: [
        {
          label: "",
          value: ""
        },
        {
          label: "Lunch",
          value: 1
        },
        {
          label: "Drink",
          value: 2
        },
        {
          label: "Side",
          value: 3
        }
      ]
    };
  },
  methods: {
    fileUploaded(file, xhr) {
      console.log("file.xhr: " + file.xhr.response);
      this.selectedItem.image = "images/" + file.xhr.response;
    },
    addedImg(files) {
      console.log(files);
    },
    addItem() {
      this.selectedItem = {
        id: null,
        type: null,
        name: null,
        image: null,
        description: null,
        products: []
      };
      this.ss = { exist: false, price: 0 };
      this.ms = { exist: false, price: 0 };
      this.ls = { exist: false, price: 0 };

      this.editModal = true;
      this.editModalType = "Add";
    },
    editItem(a) {
      this.selectedItem = JSON.parse(JSON.stringify(a));
      let pS = this.getPrice(a.products, "S");
      let pM = this.getPrice(a.products, "M");
      let pL = this.getPrice(a.products, "L");
      // this.selectedItem.exist= true;
      if (pS === null) {
        this.ss = { exist: false, price: 0 };
      } else this.ss = { exist: true, price: pS };

      if (pM === null) {
        this.ms = { exist: false, price: 0 };
      } else this.ms = { exist: true, price: pM };

      if (pL === null) {
        this.ls = { exist: false, price: 0 };
      } else this.ls = { exist: true, price: pL };

      this.editModal = true;
      this.editModalType = "Edit";
    },
    getPrice(p, s) {
      for (let i = 0; i < p.length; i++) {
        if (p[i].size === s) return p[i].price;
      }
      return null;
    },
    confirm(a) {
      this.$q
        .dialog({
          title: "Confirm",
          message: "Would you like to Deactivate this item (" + a.name + ")? ",
          cancel: true,
          persistent: true
        })
        .onOk(() => {
          console.log(">>>> OK :" + a.name);
        });
    },
    search(pageNum) {
      fetch(
        "http://localhost:8080/product/search?" +
          "pageNo=" +
          pageNum +
          "&pageSize=" +
          10 +
          "&id=" +
          (this.pId === null ? "" : this.pId) +
          "&name=" +
          this.pName +
          "&type=" +
          this.pType.value,
        {
          method: "GET"
        }
      )
        .then(response => response.json())
        .then(data => {
          if (data.errorCode === "NOT_FOUND") {
            utils.notify(data.errorMessage);
            if (this.prr !== null) {
              this.prr.content = [];
            }
          } else this.prr = data;
        })
        .catch(err => {
          this.prr = null;
          console.error(err);
        });
    },
    getPD(s, p, prs, bpid) {
      //{"id":4,"baseProductId":2,"price":17,"size":"M"}
      for (let i = 0; i < prs.length; i++) {
        if (prs[i].size === s) {
          prs[i].price = p;
          return JSON.parse(JSON.stringify(prs[i]));
        }
      }

      return {
        baseProductId: bpid,
        price: p,
        size: s
      };
    },
    editOrAddProduct() {
      let s = JSON.parse(JSON.stringify(this.selectedItem));
      let obj = {
        id: s.id,
        type: s.type,
        name: s.name,
        image: s.image,
        description: s.description,
        products: s.products
      };

      let ps = [];
      let i = 0;
      if (this.ss.exist)
        ps[i++] = this.getPD("S", this.ss.price, s.products, s.id);
      if (this.ms.exist)
        ps[i++] = this.getPD("M", this.ms.price, s.products, s.id);
      if (this.ls.exist)
        ps[i++] = this.getPD("L", this.ls.price, s.products, s.id);

      obj.products = ps;

      console.log(JSON.stringify(obj));

      fetch("http://localhost:8080" + "/product/add", {
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
    getProductTypeName(val) {
      if (val === 1) return "lunch";
      if (val === 3) return "side";
      if (val === 2) return "drink";
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
    },
    selectedProductTypeModal: {
      get() {
        let val = this.selectedItem.type;
        if (val === 1)
          return {
            label: "Lunch",
            value: 1
          };
        if (val === 3)
          return {
            label: "Side",
            value: 3
          };
        if (val === 2)
          return {
            label: "Drink",
            value: 2
          };
      },
      set(val) {
        this.selectedItem.type = val.value;
      }
    }
  },
  mounted() {
    this.search(0);
  }
};
</script>
