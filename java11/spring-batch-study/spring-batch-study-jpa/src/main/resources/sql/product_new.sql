create table product_new (
 id         bigint not null auto_increment,
 amount     bigint,
 tx_name     varchar(255),
 tx_date_time datetime,
 primary key (id)
) engine = InnoDB;