
CREATE TABLE `User_date`
(
 `id`       int NOT NULL ,
 `fullname` varchar(45) NOT NULL ,
 `username` varchar(45) NOT NULL ,
 `password` varchar(45) NOT NULL ,

PRIMARY KEY (`id`)
);


CREATE TABLE `baseProduct`
(
 `id`       int NOT NULL ,
 `type`     int NOT NULL ,
 `imageUrl` varchar(45) NOT NULL ,
 `name`     varchar(45) NOT NULL ,
 `desc`     varchar(150) NOT NULL ,

PRIMARY KEY (`id`)
);


CREATE TABLE `product`
(
 `id`             int NOT NULL ,
 `price`          int NOT NULL ,
 `size`           varchar(1) NOT NULL ,
 `baseProuductID` int NOT NULL ,

PRIMARY KEY (`id`),
KEY `fkIdx_97` (`baseProuductID`),
CONSTRAINT `FK_96` FOREIGN KEY `fkIdx_97` (`baseProuductID`) REFERENCES `baseProduct` (`id`)
);


CREATE TABLE `promotion`
(
 `id`       int NOT NULL ,
 `discount` float NOT NULL ,
 `name`     varchar(45) NOT NULL ,
 `desc`     varchar(150) NOT NULL ,

PRIMARY KEY (`id`)
);



CREATE TABLE `promotionItem`
(
 `id`          int NOT NULL ,
 `promotionId` int NOT NULL ,
 `productId`   int NOT NULL ,
 `quantity`    int NOT NULL , 

PRIMARY KEY (`id`),
KEY `fkIdx_103` (`promotionId`),
CONSTRAINT `FK_102` FOREIGN KEY `fkIdx_103` (`promotionId`) REFERENCES `promotion` (`id`),
KEY `fkIdx_106` (`productId`),
CONSTRAINT `FK_105` FOREIGN KEY `fkIdx_106` (`productId`) REFERENCES `product` (`id`)
);

 

CREATE TABLE `order`
(
 `id`     int NOT NULL ,
 `date`   varchar(45) NOT NULL ,
 `userId` int NOT NULL ,
 `state`  int NOT NULL ,

PRIMARY KEY (`id`),
KEY `fkIdx_39` (`userId`),
CONSTRAINT `FK_38` FOREIGN KEY `fkIdx_39` (`userId`) REFERENCES `User` (`id`)
);



CREATE TABLE `orderItem`
(
 `id`        int NOT NULL ,
 `quantity`  int NOT NULL ,
 `orderId`   int NOT NULL ,
 `itemId`    int NOT NULL ,
 `productId` int NOT NULL ,
 `itemType`  varchar(1) NOT NULL ,

PRIMARY KEY (`id`),
KEY `fkIdx_111` (`productId`),
CONSTRAINT `FK_110` FOREIGN KEY `fkIdx_111` (`productId`) REFERENCES `product` (`id`),
KEY `fkIdx_64` (`orderId`),
CONSTRAINT `FK_63` FOREIGN KEY `fkIdx_64` (`orderId`) REFERENCES `order` (`id`),
KEY `fkIdx_70` (`itemId`),
CONSTRAINT `FK_69` FOREIGN KEY `fkIdx_70` (`itemId`) REFERENCES `promotion` (`id`)
);

