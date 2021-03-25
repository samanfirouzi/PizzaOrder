<template>
  <div>
    <div class="row q-ma-lg justify-between">
      <div class="col-7 q-pa-md">
        <div
          class="bg-orange-1  rounded-borders  shadow-5"
          style="height: 100%;"
        >
          <div class="row bg-orange-5">
            <div class="q-pa-sm text-h6 bold">{{ promotion.name }}</div>
          </div>
          <div class="column" style="height: 200px">
            <div class="col-8  q-pa-lg text-body1">
              {{ promotion.description }}

              <div class="q-pa-lg col"></div>
              <div class="col-8  q-pa-lg text-body1 text-green-9 text-bold">
                promotion discount: {{ promotion.discount }}%
              </div>
              <q-btn-group push>
                <q-btn
                  color="green"
                  size="21px"
                  glossy
                  text-color="black"
                  push
                  @click="addOrderPromotionItem(promotion)"
                >
                  Pay:
                  {{
                    (getPrice(promotion.promotionItems) *
                      (100 - promotion.discount)) /
                      100
                  }}$ insted
                  <span class="text-strike	"
                    >: {{ getPrice(promotion.promotionItems) }}$</span
                  >
                </q-btn>
              </q-btn-group>
            </div>
          </div>
        </div>
      </div>
      <div class="col-5 border  q-pa-md">
        <q-img
          v-if="promotion !== null && promotion.image !== 'undefined'"
          class="rounded-borders shadow-5"
          :src="'http://localhost:4444/' + promotion.image"
          style="height: 300px"
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
            <div class="q-pa-sm text-h6">Promotion Items</div>
          </div>
          <div class="row q-pa-md">
            <div
              v-for="(item, index) in products"
              :key="index"
              class="col-3  q-pa-md items-start q-gutter-md justify-center"
            >
              <order-detail
                :orderItem="item"
                destination="promotion"
                :changable="false"
              />
            </div>
          </div>
        </div>
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
  </div>
</template>
<script>
import orderService from "./../services/orderService";
import promotionService from "./../services/promotionServices";
import prductService from "./../services/productService";
import utils from "./../services/util";
import es from "./../services/es";
import orderDetail from "./../components/orderDetail.vue";
export default {
  components: { orderDetail },
  name: "productDetail",
  data() {
    return {
      promotion: {},
      products: [],
      comment: "",
      hits: []
    };
  },
  methods: {
    getProduct(item, i) {
      console.log("item: " + JSON.stringify(item));
      prductService.getProductById(item.product.baseProductId, data => {
        console.log("pr " + i + " : " + JSON.stringify(data));
        this.products.push({
          name: data.name,
          image: data.image,
          size: this.promotion.promotionItems[i].product.size,
          price: this.promotion.promotionItems[i].product.price,
          orderItem: {
            quantity: this.promotion.promotionItems[i].quantity
          }
        });
      });
    },
    getPrice(item) {
      let sumPrice = 0;
      if (item !== null && item !== undefined)
        for (let i = 0; i < item.length; i++) {
          sumPrice += item[i].product.price * item[i].quantity;
        }
      return sumPrice;
    },
    // saveProductCallBack(data) {},
    postComment() {
      es.postComment(
        this.$store.getters["user/getUserInfo"],
        this.$route.params.pId,
        this.comment,
        "promotion"
      );
      this.comment = "";

      setTimeout(
        () => this.getCommentByProductId(this.$route.params.pId),
        1000
      );
    },
    getCommentByProductId(pId) {
      es.getComments(pId, "promotion", this.commentCallback);
      this.comment = "";
    },
    commentCallback(data) {
      this.hits = data.hits.hits;
    },
    promotionCallBack(data) {
      this.promotion = data;
      console.log(JSON.stringify(this.promotion));

      for (let i = 0; i < this.promotion.promotionItems.length; i++) {
        // this.products.put[i] = {};
        this.getProduct(this.promotion.promotionItems[i], i);
      }
    },
    getProductById() {
      promotionService.getPromotionById(
        this.$route.params.pId,
        this.promotionCallBack
      );
    },
    addOrderPromotionItem(p) {
      let item = {
        quantity: 1,
        promotionId: p.id,
        type: 2
      };
      orderService.addOrderItem(item, "Cart", this.$store, p.name);
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
