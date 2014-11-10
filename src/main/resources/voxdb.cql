CREATE KEYSPACE phonex WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor': 1};

USE phonex;

// Primary table for storing users:
CREATE TABLE users(
    username text,
    domain text,
    email text,
    fullname text,
    sip_password text,
    voicemail_password text,
    extension bigint,
    groups Set<text>,
    PRIMARY KEY(username, domain)
);

CREATE TABLE users_by_domain(
    domain text,
    users Set<text>,
    PRIMARY KEY(domain)
);

CREATE TABLE organization(
   domain text PRIMARY KEY,
   organizationName text,
   groups Set<text>,
   didNumbers Set<text>,
   primaryContactName text,
   primaryContactEmail text,
   primaryContactCell text
);
