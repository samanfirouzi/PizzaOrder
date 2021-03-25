import { Notify } from "quasar";
const getProductTypeName = function(val) {
  if (val === "1") return "lunch";
  if (val === "3") return "side";
  if (val === "2") return "drink";
};
const getSizeName = function(size) {
  if (size === "M") return "Medium";
  if (size === "S") return "Small";
  if (size === "L") return "Large";
  return 'Promotion'
};
const notify = function(msg) {
  Notify.create({
    message: msg,
    color: "orange"
  });
};

export default{
    getProductTypeName,getSizeName,notify
}
