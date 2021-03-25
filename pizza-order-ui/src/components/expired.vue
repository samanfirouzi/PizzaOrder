<template>
  <div
    class=" bg-blue text-white text-center q-pa-md flex flex-center fullscreen"
    v-else-if="!isAdmin"
  >
    <div>
      <div style="font-size: 15vh " class="row  text-red-13">
        Access Denied!!!
      </div>
      <div class="row flex-center">
        <q-btn label="login" color="yellow-10" @click="login" />
      </div>
    </div>
  </div>
</template>
<script>
export default {
  methods: {
    login() {
      this.$store.commit("user/changeLoginFormVisibility", true);
    }
  }
};
</script>
