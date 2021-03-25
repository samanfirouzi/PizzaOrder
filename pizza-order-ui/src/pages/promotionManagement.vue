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
          @click="addPromotion()"
        ></q-btn>
      </div>
    </div>
    <q-separator />
    <div class=" q-ma-md rounded-borders shadow-4 bg-yellow-1 text-grey-10">
      <div class="row q-pa-sm bg-orange-4  ">
        <div class="col">Id</div>
        <div class="col">Name</div>
        <div class="col">Image</div>
        <div class="col">discount</div>
        <div class="col">operation</div>
      </div>
      <q-separator class="q-pa-xs bg-red-7" />

      <div
        v-if="prr !== null && prr.content !== []"
        v-for="(a, index) in prr.content"
        :key="index"
        :class="getBgColor(index)"
      >
        <div class="row q-pa-sm shadow-up-1">
          <div class="col">{{ a.id }}</div>
          <div class="col">{{ a.name }}</div>

          <div class="col">
            <q-img
              class="rounded-borders	shadow-1"
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

          <div class="col">{{ a.discount }}%</div>
          <div class="col">
            <q-btn
              icon="create"
              label="Edit"
              class="q-mr-sm"
              color="orange"
              size="10px"
              @click="editPromotion(a)"
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
        style="width: 1100px; max-width: 80vw;"
      >
        <q-card-section class="bg-orange text-grey-1">
          <div class="text-h6">{{ editModalType }} Promotion</div>
        </q-card-section>

        <q-card-section class="q-pt-none q-pa-md">
          <div class="row">
            <div class="col-8 ">
              <div class=" q-pa-md">
                <q-input
                  label="Promotion Name"
                  clearable
                  outlined
                  v-model="selectedItem.name"
                />
              </div>
              <div class=" q-pa-md">
                <q-input
                  type="number"
                  label="Discount"
                  clearable
                  outlined
                  v-model="selectedItem.discount"
                />
              </div>
              <div class=" q-pa-md">
                <q-input
                  label="Promotion description"
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
                  @uploaded="fileUploaded"
                  color="orange"
                />
              </div>
            </div>
          </div>
        </q-card-section>

        <q-card-section class="q-pt-none q-pa-md">
          <div
            class=" q-ma-md rounded-borders shadow-4 bg-yellow-1 text-grey-10"
          >
            <div class="row q-pa-sm bg-orange-4  ">
              <div class="col">Name</div>
              <div class="col">image</div>
              <div class="col">size</div>
              <div class="col">price</div>
              <div class="col">quantity</div>
              <div class="col">
                <q-btn
                  icon="add"
                  label="Add"
                  color="green"
                  size="10px"
                  @click="addPromotionItem"
                />
              </div>
            </div>
            <q-separator class="q-pa-xs bg-red-7" />

            <div
              v-for="(a, index) in selectedItem.promotionItems"
              :key="index"
              :class="getBgColor(index)"
            >
              <div class="row q-pa-sm shadow-up-1">
                <div class="col">
                  {{ a.product.name }}
                </div>
                <div class="col">
                  <q-img
                    class="rounded-borders	shadow-1"
                    :src="'http://localhost:4444/' + a.product.image"
                    style="max-height:25px; max-width:75px"
                  />
                </div>
                <div class="col">{{ a.product.size }}</div>
                <div class="col">{{ a.product.price }}$</div>
                <div class="col">{{ a.quantity }}</div>
                <div class="col">
                  <q-btn
                    icon="remove"
                    label="Delete"
                    @click="confirm(a, index)"
                    color="red"
                    size="10px"
                  />
                </div>
              </div>
            </div>
            <q-separator class="bg-red-7" />
            <div class="q-pa-xs row ">
              <div class="col">total cost: 39$</div>
              <div class="col flex justify-end">after discount: 33$</div>
            </div>
          </div>
        </q-card-section>

        <q-card-actions align="right" class="bg-orange text-white ">
          <q-btn flat label="Cancel" v-close-popup />
          <q-btn flat :label="editModalType" @click="editOrAddPromotion" />
        </q-card-actions>
      </q-card>
    </q-dialog>

    <q-dialog v-model="pItemModal" persistent>
      <q-card
        v-if="selectedItem !== null"
        style="width: 700px; max-width: 80vw;"
      >
        <q-card-section class="bg-yellow text-grey-1">
          <div class="text-h6">choose Promotion Item</div>
        </q-card-section>

        <q-card-section class="q-pt-none q-pa-md">
          <div class=" row q-ma-sm  text-grey-10">
            <div class="col q-ma-sm">
              <q-select
                filled
                v-model="modelSearch"
                use-input
                hide-selected
                fill-input
                input-debounce="0"
                label="Search products"
                :options="productOptions"
                @input="val => addProductIdToItem(val)"
                @filter="
                  (val, update, abort) => filterFn(val, update, abort, '')
                "
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
            <div class="col q-ma-sm">
              <q-select
                label="Product Size"
                :options="searchedPItem.sOp"
                outlined
                v-model="searchedPItem.selected"
              />
            </div>
            <div class="col q-ma-sm">
              <q-badge color="secondary">
                quantity: {{ searchedPItem.quantity }} (1 to 10)
              </q-badge>

              <q-slider v-model="searchedPItem.quantity" :min="1" :max="10" />
            </div>
          </div>
        </q-card-section>

        <q-card-actions align="right" class="bg-orange text-white ">
          <q-btn flat label="Cancel" v-close-popup />
          <q-btn flat label="add" @click="addItemToList" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
  <expired v-else-if="!isAdmin" />
</template>
<script>
import expired from "src/components/expired.vue";
import prductService from "./../services/productService";
import utils from "./../services/util";

export default {
  components: { expired },
  data() {
    return {
      selectedItem: null,
      modelSearch: null,
      pItemModal: false,
      editModal: false,
      editModalType: "",
      prr: {},
      searchedPItem: {},
      pId: "",
      pName: "",
      pType: {
        label: "",
        value: ""
      },
      productOptions: [],
      sizeOption: [],
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
    filterFn(val, update, abort, item) {
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
            this.productOptions = [];
            for (let i = 0; i < content.length; i++) {
              this.productOptions[i] = {
                label: content[i].name,
                value: content[i].id,
                image: content[i].image,
                sOp: []
              };
              for (let j = 0; j < content[i].products.length; j++) {
                this.productOptions[i].sOp.push({
                  value: content[i].products[j].id,
                  label: content[i].products[j].size,
                  price: content[i].products[j].price
                });
              }
            }

            console.log(
              "this.productOptions: " + JSON.stringify(this.productOptions)
            );
          })
          .catch(err => {
            //this.prr = null;
            console.error(err);
          });
      });
    },
    addProductIdToItem(val) {
      console.log("addProductIdToItem: " + val.value);
      this.searchedPItem = val;
    },
    fileUploaded(file, xhr) {
      console.log("file.xhr: " + file.xhr.response);
      this.selectedItem.image = "images/" + file.xhr.response;
    },
    addItemToList() {
      this.selectedItem.promotionItems.push({
        promotionId: this.selectedItem.id,
        quantity: this.searchedPItem.quantity,
        product: {
          id: this.searchedPItem.selected.value,
          name: this.searchedPItem.label,
          image: this.searchedPItem.image,
          price: this.searchedPItem.selected.price,
          size: this.searchedPItem.selected.label
        },
        options: []
      });
      console.log(
        "this.selectedItem.promotionItems: " +
          JSON.stringify(this.selectedItem.promotionItems)
      );
      this.pItemModal = false;
    },
    addPromotionItem() {
      // sizeOption = [];
      this.searchedPItem = {};
      this.pItemModal = true;
    },
    addPromotion() {
      this.selectedItem = {
        id: null,
        discount: 0,
        name: null,
        image: null,
        description: null,
        promotionItems: []
      };
      this.editModal = true;
      this.editModalType = "add";
    },
    getProduct(item, i) {
      prductService.getProductById(item.product.baseProductId, data => {
        this.selectedItem.promotionItems[i].product.name = data.name;
        this.selectedItem.promotionItems[i].product.image = data.image;

        let oo = this.selectedItem.promotionItems.pop();
        this.selectedItem.promotionItems.push(oo);
      });
    },
    editPromotion(a) {
      this.selectedItem = JSON.parse(JSON.stringify(a));
      console.log("editPromotion: " + JSON.stringify(this.selectedItem));

      for (let i = 0; i < this.selectedItem.promotionItems.length; i++) {
        // this.products.put[i] = {};
        this.getProduct(this.selectedItem.promotionItems[i], i);
      }

      this.editModal = true;
      this.editModalType = "update";
    },
    getPrice(p, s) {
      for (let i = 0; i < p.length; i++) {
        if (p[i].size === s) return p[i].price;
      }
      return null;
    },
    confirm(a, index) {
      this.$q
        .dialog({
          title: "Confirm",
          message:
            "Would you like to delete this item (" + a.product.name + ")? ",
          cancel: true,
          persistent: true
        })
        .onOk(() => {
          this.selectedItem.promotionItems.splice(index, 1);
        });
    },
    confirmDeleteItem(a) {
      this.$q
        .dialog({
          title: "Confirm",
          message: "Would you like to Delete this item (" + a.name + ")? ",
          cancel: true,
          persistent: true
        })
        .onOk(() => {
          // this.selectedItem.promotionItems
        });
    },
    search(pageNum) {
      fetch(
        "http://localhost:8080/promotion/search?" +
          "pageNo=" +
          pageNum +
          "&pageSize=" +
          5 +
          "&id=" +
          (this.pId === null ? "" : this.pId) +
          "&name=" +
          this.pName,
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
          console.error('eeeeeeee: '+err);
          // this.prr = null;
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
    editOrAddPromotion() {
      let s = JSON.parse(JSON.stringify(this.selectedItem));
      console.log(JSON.stringify(s));
      let obj = {
        id: s.id,
        discount: s.discount,
        name: s.name,
        image: s.image,
        description: s.description,
        promotionItems: []
      };

      for (let i = 0; i < s.promotionItems.length; i++) {
        obj.promotionItems.push({
          id: s.promotionItems[i].id,
          promotionId: obj.id,
          product: { id: s.promotionItems[i].product.id },
          quantity: s.promotionItems[i].quantity
        });
      }
      console.log(JSON.stringify(obj));

      fetch("http://localhost:8080" + "/promotion/" + this.editModalType, {
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
