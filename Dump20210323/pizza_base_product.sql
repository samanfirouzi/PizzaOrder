-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pizza
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `base_product`
--

DROP TABLE IF EXISTS `base_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `base_product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` int NOT NULL,
  `image` varchar(145) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(550) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `base_product`
--

LOCK TABLES `base_product` WRITE;
/*!40000 ALTER TABLE `base_product` DISABLE KEYS */;
INSERT INTO `base_product` VALUES (1,1,'images/upload_c9255839daae569713f1cdca21f168fe.jpg','Vegetarian Pizza','As a vegetarian for over a decade, I’ve eaten quite a few veggie pizzas. Truly fantastic veggie pizzas are few and far between. So, I combined all of the elements I’ve enjoyed over the years to create my own “ultimate” veggie pizza pie. This is the best homemade veggie pizza I’ve ever had, and I hope you’ll say the same!'),(2,1,'images/upload_c46c1bdcd56c900917654839042e4d88.jpg','Cheese Pizza special','Simple, yet simply delicious. Real cheese made from mozzarella on top of our signature pizza sauce with your choice of crust, then baked to a golden brown. It has just what you want, and nothing you don’t.'),(3,1,'images/upload_e6ec8d4183ff0f46c99fdd9037df8775.webp','Chicken Pizza','Switch up your regular pizza routine and prepare this easy and flavorful gourmet-style pizza in minutes and prepare this easy and flavorful gourmet-style pizza in minutes.'),(4,1,'images/upload_657b4113497bcb8d5dd2867796ae6b05.jpg','Hawaiian BBQ Chicken','Aloha! You’re on island time with the Hawaiian BBQ Chicken pizza. Smothered in smoky BBQ sauce, it’s topped with grilled chicken, hickory-smoked bacon, real cheese made from mozzarella, plus fresh-cut onions, and sweet, juicy pineapple tidbits for a taste of the tropics.'),(5,1,'images/upload_b896a7f780deb8fa60d9c11b2f7ea5aa.jpg','sun pizza','The secret to this crispy pizza: giving the dough lots of time to rise so it\'s pliant enough to stretch very thin.At Medlock Ames\'s tasting room, general manager and cook Kenny Rochford offers ultracrispy pizzas for free on weekend afternoons.'),(6,1,'images/upload_c5f2ca09c69aadd501b63aef88b63164.jpg','Special pizza','Classic homemade pizza recipe, including pizza dough and toppings, step-by-step instructions. Make perfect pizza at home with New Milano’s fresh pepperoni!  Prep time: 2 hours Cook time: 30 minutes Yield: Makes 2 10-12-inch pizzas'),(7,3,'images/upload_cc6fcd8cffda0b800f9d2cb742527f4d.jpg','French fries','French fries, or simply fries, chips, finger chips, or French-fried potatoes, are batonnet or allumette-cut deep-fried potatoes.'),(8,2,'images/upload_c8e5bf098ed5836f23d1a865f39fe84a.jpg','Coca cola','Coca-Cola, or Coke, is a carbonated soft drink manufactured by The Coca-Cola Company. Originally marketed as a temperance drink and intended as a patent medicine, it was invented in the late 19th century'),(9,2,'images/upload_2d39cb9aabd8af47d39e6dab907121b4.jpg','Pepsi','Pepsi Max is a low-calorie, sugar-free cola, marketed by PepsiCo as an alternative to their drinks Pepsi and Diet Pepsi. It is sold primarily in European and Asian markets. A drink with the same name but different formulation was sold in the United States until it was renamed \"Pepsi Zero Sugar\"'),(10,3,'images/upload_bd7887762c74ded204928530b0ebefd2.jpg','Grilled Carrots','Grilled Carrots with Cumin-Serrano Yogurt Carrots love to burn when grilled over direct high heat; better to park them over a cooler spot on the grate and keep the cover closed if possible.'),(11,1,'images/upload_efbf2c9c46f8e49424513db47f4265b1.webp','Homemade Pizza','Pizza dough is a yeasted dough which requires active dry yeast. Make sure the check the expiration date on the yeast package! Yeast that is too old may be dead and won\'t work.'),(12,3,'images/upload_6954ed6521c42415829b0d150302adb0.jpg','M-potato','a dish made from boiled potatoes and a variety of other ingredients. It is generally considered a side dish, as it usually accompanies the main course. Potato salad is widely believed to have originated in Germany, spreading largely throughout Europe, European colonies and later Asia.'),(13,3,'images/upload_a0071006d3fad59de02a1cd2362587cc.jpg','Roasted Butternut Squash','Roasted Butternut Squash and Brussels sprouts with Pecans and Cranberries is one of the best holiday side dishes you’ll ever try!  It’s a perfect recipe for Thanksgiving, Christmas, New Year’s Eve or any time you like! This side dish is packed with vegetables and nuts. It’s healthy, gluten-free, vegetarian, and rich in fiber!'),(14,3,'images/upload_a85817b8962d8cfb2816fda805397854.jpg','Braised Leeks, Peas Lettuce','when you warm lettuce in butter and wine it gets velvety and rich—a perfect foil to the toothsome, sweet peas. Serve this spooned over slices of quiche for brunch or alongside your favorite spring roast for dinner.'),(15,3,'images/upload_e0508a506440bfcecf3b6cfca9b3364f.jpg','Pea and Prosciutto Salad','Toss blanched snap peas and English peas with arugula and a mustardy vinaigrette, then drape with prosciutto for an elegant spring salad.'),(16,3,'images/upload_ca797f174713ef534b77acb49f94af91.jpg','Sugar Snap Pea Salad','Come springtime, sugar snap peas are so tender and fresh it\'s almost a shame to cook them. We prefer slicing them thinly and eating them raw with a simple dressing, like this creamy, tangy buttermilk one.'),(17,3,'images/upload_dafd30952014069a2a86b7f64adab697.jpg','Green Beans With Mushroom','In Hong Kong, where XO sauce was invented, dried shrimp or scallops and Chinese ham are combined with chiles to create a powerhouse condiment that is salty, spicy, and funky. This recipe uses mushrooms in place of the pork, a cheeky reference to green bean casseroles.  SHARE “GREEN BEANS WITH MUSHROOM XO SAUCE ” ON FACEBOOKSHARE “GREEN BEANS WITH MUSHROOM XO SAUCE ” ON TWITTERSHARE “GREEN BEANS WITH MUSHROOM XO SAUCE ” ON PINTERESTSHARE “GREEN BEANS WITH MUSHROOM XO SAUCE ” ON GOOGLE+EMAIL “GREEN BEANS WITH MUSHROOM XO SAUCE ”'),(18,1,'images/upload_803bd7d58f36b265a832262ad734c1cd.jpg','Pepperoni Pizza','Your choice of crust covered with our signature pizza sauce, real cheese made from mozzarella, and pepperoni. With a pepperoni in almost every bite, it\'s one of our most popular pizzas.'),(19,2,'images/upload_bc163d689ca553deb885e82c135f8259.jpg','Shaken Iced Passion','Starbucks has all kinds of sugary beverages—hot and cold alike—but it also has zero-calorie drinks on the menu. The coffee chain’s Teavana Shaken Iced Passion Tango Tea is a good choice for a guilt-free option'),(20,2,'images/upload_735daaa9272b5f00ec2bb907688ebc56.jpg','Orange Fruit Smoothie','Made with Greek yogurt, the health-conscious Pineapple Orange Fruit Smoothie from Tim Hortons is a no-brainer if you’re looking for something that’s sweet, filling and not completely loaded with added sugar.'),(21,2,'images/upload_026c695b9953d4064e6364c64bb88324.jpeg','Sweet Tea at McAlister’s Deli','There’s nothing like a tall glass of Sweet Tea from McAlister’s Deli to go with any of the popular fast food chain’s sandwiches, soups or salads. You won’t need to sacrifice any sweet taste or calories while sipping on it, either.'),(22,2,'images/upload_9e38742e71f098d4f1acbedf8e94052f.jpeg','Raspberry Iced Tea at Dunkin','Sure, Dunkin’ is a powerhouse when it comes to cranking out some of the most-loved coffee out there. But the chain also serves a handful of reasonably healthy fast food drinks, like its Iced Black Tea with a shot of raspberry, a welcome change from the normal sugar-filled treats there.'),(23,2,'images/upload_e6a16b4c3c2d65db394d97528fd517f3.jpg','Cucumber Orange Cooler','Picture freshly squeezed orange juice infused with the refreshing tastes of a crisp apple, fresh cucumber and a zesty lemon. Yep—the Cucumber Orange Cooler from Jamba Juice is as nutrient-packed as it is tasty.'),(24,2,'images/upload_2af2022e78f89b7288f438cd881a83f4.jpg','Lightly Sweet Iced Chai Latte','health-conscious drinking has gotten easier with favorite drinks made a little less sugary and a little more light. Enter the Lightly Sweet Iced Chai Latte, which offers the same great familiar flavor as the original chai latte.'),(27,1,'images/upload_54aea5fe135a2d9b4b72d2ff2b463fe1.jpg','test demo','test demo ');
/*!40000 ALTER TABLE `base_product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-23 11:59:11
