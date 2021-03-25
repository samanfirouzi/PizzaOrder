<template>
  <div>
    <div class="row q-ma-lg justify-between">
      <div class="col-7 q-pa-md">
        <div
          class="bg-orange-1  rounded-borders  shadow-5"
          style="height: 100%;"
        >
          <div class="row bg-orange-5">
            <div class="q-pa-sm text-h6 bold">{{ product.name }}</div>
          </div>
          <div class="column" style="height: 200px">
            <div class="col-8  q-pa-lg text-body1">
              {{ product.description }}
            </div>
            <div class="q-pa-lg col">
              <q-btn-group push>
                <q-btn
                  :color="'yellow-' + (3 * index + 4)"
                  glossy
                  size="21px"
                  text-color="black"
                  push
                  @click="addOrderItem(pInfo.id)"
                  v-for="(pInfo, index) in product.products"
                  :key="index"
                  >{{ getName(pInfo.size) }} : ${{ pInfo.price }}</q-btn
                >
              </q-btn-group>
            </div>
          </div>
        </div>
      </div>
      <div class="col-5 border  q-pa-md">
        <q-img
          v-if="product !== null && product.image !== 'undefined'"
          class="rounded-borders shadow-5"
          :src="'http://localhost:4444/' + product.image"
          style="height: 300px"
          @click="imagedialog = true"
          ><template v-slot:error>
            <div class="absolute-full flex flex-center bg-negative text-white">
              Cannot load image
            </div></template
          >
        </q-img>
      </div>
    </div>
    <div class="row q-ma-lg justify-between">
      <div class="col-12 q-pa-sm">
        <div
          class="bg-orange-1  rounded-borders  shadow-5"
          style="height: 100%;"
        >
          <div class="row bg-orange-5">
            <div class="q-pa-sm text-h6">User Comments</div>
          </div>
          <div class="column">
            <div class="row q-ma-lg">
              <div class="col-10 q-pa-md">
                <q-input v-model="comment" filled autogrow />
              </div>
              <div class="col-2 q-pa-md flex ">
                <q-btn
                  label="Comment"
                  icon="comment"
                  color="blue"
                  @click="postComment"
                ></q-btn>
              </div>
            </div>
          </div>
          <div
            class="q-ma-lg bg-yellow-1 shadow-2"
            v-for="(hit, index) in hits"
          >
            <div class="row bg-orange-3">
              <div class="col q-ma-sm">
                {{ hit._source.user === "" ? "guest" : hit._source.user }}
              </div>
              <div class="col q-ma-sm">{{ hit._source.date }}</div>
            </div>
            <div class="row">
              <div class="col q-ma-md">{{ hit._source.comment }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <q-dialog v-model="imagedialog" full-height fullWidth>
      <q-img
        v-if="product !== null && product.image !== 'undefined'"
        class="rounded-borders shadow-5 full-height fullWidth"
        :src="'http://localhost:4444/' + product.image"
        @click="imagedialog = false"
        ><template v-slot:error>
          <div class="absolute-full flex flex-center bg-negative text-white">
            Cannot load image
          </div></template
        >
      </q-img>
    </q-dialog>
  </div>
</template>
<script>
import orderSerice from "./../services/orderService";
import productSerice from "./../services/productService";
import utils from "./../services/util";
import es from "./../services/es";
export default {
  name: "productDetail",
  data() {
    return {
      product: {},
      comment: "",
      imagedialog: false,
      hits: []
    };
  },
  methods: {
    postComment() {
      es.postComment(
        this.$store.getters["user/getUserInfo"],
        this.$route.params.pId,
        this.comment,
        "product"
      );
      this.comment = "";

      setTimeout(
        () => this.getCommentByProductId(this.$route.params.pId),
        1000
      );
    },
    getCommentByProductId(pId) {
      es.getComments(pId, "product", this.commentCallback);
      this.comment = "";
    },
    commentCallback(data) {
      this.hits = data.hits.hits;
    },
    saveProductCallBack(data) {
      this.product = data;
      es.esEvent("ProductDetail", "Visit", data.name, data.id);

    },
    getProductById() {
      productSerice.getProductById(
        this.$route.params.pId,
        this.saveProductCallBack
      );
    },
    addOrderItem(pId) {
      let item = {
        quantity: 1,
        productId: pId,
        type: 1
      };
      orderSerice.addOrderItem(item, "Cart", this.$store, this.product.name);
    },
    getName(size) {
      return utils.getSizeName(size);
    }
  },
  mounted() {
    this.getProductById(this.$route.params.pId);
    this.getCommentByProductId(this.$route.params.pId);
  },
  watch: {
    "$route.params.pId"(newId, oldId) {
      this.getProductById(newId);
      this.getCommentByProductId(newId);
    }
  }
};
</script>
