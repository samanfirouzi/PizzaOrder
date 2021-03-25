import utils from "./util";
export default{
    esEvent(_page, _action, _objectName, _objectId) {
        fetch("http://localhost:9200/pizza_event/_doc", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            page: _page,
            action: _action,
            objName: _objectName,
            objId: _objectId,
            date: new Date()
          })
        })
          .then(response => response.json())
          .then(data => {
            console.log(data);
          })
          .catch(err => {
            console.error(err);
          });
      },    
      buyEvent(usern,totalb,_orderId) {
        fetch("http://localhost:9200/pizza_buy/_doc", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            user: usern,
            total: totalb,
            orderId: _orderId,
            date: new Date()
          })
        })
          .then(response => response.json())
          .then(data => {
            console.log(data);
          })
          .catch(err => {
            console.error(err);
          });
      },    
      postComment(usern,pId,commentIn,pType){
        fetch("http://localhost:9200/pizza_comment/_doc", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            user: usern,
            productId: pId,
            prType: pType,
            comment: commentIn,
            date: new Date()
          })
        })
          .then(response => response.json())
          .then(data => {
            console.log(data);
          })
          .catch(err => {
            console.error(err);
          });
      },
      getComments(pId,pType,callBack){
        fetch("http://localhost:9200/pizza_comment/_search?q=productId:"+pId+" AND prType:"+pType, {
          method: "Get",
          headers: {
            "Content-Type": "application/json"
          }
        })
          .then(response => response.json())
          .then(data => {
            callBack(data);
          })
          .catch(err => {
            console.error(err);
          });
      },
}