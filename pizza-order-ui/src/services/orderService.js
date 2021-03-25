import es from "./../services/es";
import utils from "./../services/util";
export default {
  addOrderItem(item, where, store, pName) {
    if (
      store.getters["user/getAccessToken"] === null ||
      store.getters["user/getAccessToken"] === ""
    )
      store.commit("user/changeLoginFormVisibility", true);
    else {
      fetch("http://localhost:8080/order/addItem/" + where, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "http://localhost:8080/*",
          "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE",
          Authorization: "bearer " + store.getters["user/getAccessToken"]
        },
        body: JSON.stringify(item)
      })
        .then(response => response.json())
        .then(data => {
          store.commit("order/setActiveOrder", data);
          es.esEvent(where, "Add item", pName, item.id);
        })
        .catch(err => {
          console.error(err);
        });
    }
  },
  removeOrderItem(item, store) {
    fetch("http://localhost:8080/order/removeItem", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "http://localhost:8080/*",
        "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE",
        Authorization: "bearer " + store.getters["user/getAccessToken"]
      },
      body: JSON.stringify(item)
    })
      .then(response => response.json())
      .then(data => {
        store.commit("order/setActiveOrder", data);
        es.esEvent("OrderDetail", "Remove item", null, item.id);
      })
      .catch(err => {
        console.error(err);
      });
  },
  deleteFavorite(fId, store) {
    fetch("http://localhost:8080/order/deleteFavorite?favoriteId=" + fId, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE",
        Authorization: "bearer " + store.getters["user/getAccessToken"]
      }
    })
      .then(response => response.json())
      .then(data => {
        store.commit("order/setActiveOrder", data);
        es.esEvent("Favorite", "delete", null, fId);
      })
      .catch(err => {
        console.error(err);
      });
  },
  activeFavorite(fId, store) {
    fetch("http://localhost:8080/order/activeFavorite?favoriteId=" + fId, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE",
        Authorization: "bearer " + store.getters["user/getAccessToken"]
      }
    })
      .then(response => response.json())
      .then(data => {
        store.commit("order/setActiveOrder", data);
        es.esEvent("Favorite", "To cart", null, fId);
      })
      .catch(err => {
        console.error(err);
      });
  },
  activeHistory(fId, store) {
    fetch("http://localhost:8080/order/activeHistory?historyId=" + fId, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE",
        Authorization: "bearer " + store.getters["user/getAccessToken"]
      }
    })
      .then(response => response.json())
      .then(data => {
        store.commit("order/setActiveOrder", data);
        es.esEvent("History", "To cart", null, fId);
      })
      .catch(err => {
        console.error(err);
      });
  },
  saveForLater(store) {
    fetch("http://localhost:8080/order/saveForLater", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE",
        Authorization: "bearer " + store.getters["user/getAccessToken"]
      }
    })
      .then(response => response.json())
      .then(data => {
        store.commit("order/setActiveOrder", data);
        es.esEvent("Cart", "Inactivate", null, null);
      })
      .catch(err => {
        console.error(err);
      });
  },
  payIT(store) {
    fetch("http://localhost:8080/order/payCart", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE",
        Authorization: "bearer " + store.getters["user/getAccessToken"]
      }
    })
      .then(response => response.json())
      .then(data => {
        if (data.errorCode === "NOT_FOUND") {
          utils.notify(data.errorMessage);
        } else store.commit("order/setActiveOrder", data);
      })
      .catch(err => {
        console.error(err);
      });
  },
  activateSaved(store) {
    fetch("http://localhost:8080/order/savedToActive", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE",
        Authorization: "bearer " + store.getters["user/getAccessToken"]
      }
    })
      .then(response => response.json())
      .then(data => {
        store.commit("order/setActiveOrder", data);
        es.esEvent("Inactive", "Active", null, null);
      })
      .catch(err => {
        console.error(err);
      });
  },
  addFavorite(store) {
    fetch("http://localhost:8080/order/addFavorite", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Methods": "POST, PUT, GET, OPTIONS, DELETE",
        Authorization: "bearer " + store.getters["user/getAccessToken"]
      }
    })
      .then(response => response.json())
      .then(data => {
        store.commit("order/setActiveOrder", data);
        es.esEvent("Cart", "Add favorite", null, null);
      })
      .catch(err => {
        console.error(err);
      });
  }
};
