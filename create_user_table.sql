CREATE TABLE `user` (
  `user_id` varchar(16) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `date_of_birth` date NOT NULL,
  `sex` varchar(32) NOT NULL,
  `highest_education` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `country` varchar(56) NOT NULL,
  `state` varchar(56) NOT NULL,
  `city` varchar(56) NOT NULL,
  `user_status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

