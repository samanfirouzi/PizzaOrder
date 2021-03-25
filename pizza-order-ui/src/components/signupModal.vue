<template>
  <q-dialog
    v-model="isSPVisible"
    persistent
    transition-show="flip-down"
    transition-hide="flip-up"
  >
    <q-card class="bg-blue-7 text-white">
      <q-bar>
        <q-space />
        <q-btn dense flat icon="close" v-close-popup>
          <q-tooltip content-class="bg-white text-primary">Close</q-tooltip>
        </q-btn>
      </q-bar>

      <q-card-section>
        <div class="text-h6">Signup</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        Hello dear customer, you can access to ordering after signup.
      </q-card-section>
      <q-card-section class="q-pt-none">
        <q-input
          ref="fullname"
          filled
          v-model="fullname"
          label="Your full name *"
          hint="we called you with your full name"
          lazy-rules
          :rules="[val => (val && val.length > 0) || 'Please type you full name']"
        />
        <q-separator class="q-mt-lg"/>
        <q-input
          ref="username"
          filled
          v-model="username"
          label="Your username *"
          hint="UserName is uniqe"
          lazy-rules
          :rules="[val => (val && val.length > 0) || 'Please type your username']"
        />
        <q-separator class="q-mt-lg"/>
        <q-input
          v-model="password"
          filled
          :type="isPwd ? 'password' : 'text'"
          hint="Choose strong Password"
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
            label="Signup"
          />
        </q-btn-group>
      </q-card-section>
    </q-card>
  </q-dialog>
</template>

<script>
export default {
  computed: {
    isSPVisible: {
      get() {
        return this.$store.getters["user/isSignupPageVisible"];
      },
      set(val) {
        this.$store.commit("user/changeSignupFormVisibility", val);
      }
    }
  },
  data() {
    return {
      name: "",
      fullname: "",
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
          "/addUser?fullName=" +
          this.fullname +
          "&username=" +
          this.username +
          "&password=" +
          this.password,
        {
          method: "GET",
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
          this.closeModal();
        })
        .catch(err => {
          console.error(err);
        });
    },
    
    closeModal() {
      this.fullname = "";
      this.username = "";
      this.password = "";
      this.isSPVisible = false;
    },
  }
};
</script>
