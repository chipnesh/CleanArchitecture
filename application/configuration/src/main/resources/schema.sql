create table t_account (
  id VARCHAR(36) not null,
  login VARCHAR(100) not null,
  name VARCHAR(100) not null,
  email VARCHAR(100) not null,
  hash VARCHAR(256) not null
);

create table t_sessions (
  id VARCHAR(36) not null,
  login VARCHAR(100) not null,
  active BOOLEAN not null,
  expirationDate VARCHAR(100) not null
);