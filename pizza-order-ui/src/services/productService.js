import es from "./es";
export default {
  getAllProductsByType(type, callBackSearch) {
    fetch("http://localhost:8080/product/type/" + type, {
      method: "GET"
    })
      .then(response => response.json())
      .then(data => {
        callBackSearch(data);
      })
      .catch(err => {
        console.error(err);
      });
  },
  getAllProducts(callBackSearch) {
    fetch("http://localhost:8080/product/all", {
      method: "GET"
    })
      .then(response => response.json())
      .then(data => {
        callBackSearch(data);
      })
      .catch(err => {
        console.error(err);
      });
  },
  getProductById(pId,callBack) {
    fetch("http://localhost:8080/product/detail/" + pId, {
      method: "GET"
    })
      .then(response => response.json())
      .then(data => {
        callBack(data);
      })
      .catch(err => {
        console.error(err);
      });
  },
};
