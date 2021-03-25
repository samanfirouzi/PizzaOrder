import es from "./es";
export default {
  getPromotionById(pId,callBack) {
    fetch("http://localhost:8080/promotion/detail/" + pId, {
      method: "GET"
    })
      .then(response => response.json())
      .then(data => {
        callBack(data);
        es.esEvent("PromotionDetail","Visit",data.name,data.id)
      })
      .catch(err => {
        console.error(err);
      });
  },
};
