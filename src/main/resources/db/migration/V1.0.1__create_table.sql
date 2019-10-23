create table user(
	id int auto_increment not null,
	username varchar(50) not null,
	password varchar(100) not null,
	active int default 1 not null,
	roles varchar(255) ,
	permission varchar(255) ,
	primary key (id)	
	
)