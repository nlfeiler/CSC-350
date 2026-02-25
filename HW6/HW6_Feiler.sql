DROP TABLE procedure_history;
DROP TABLE procedure_details;
DROP TABLE pets;
DROP TABLE owners;

CREATE TABLE owners 
    (owner_id INT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    street_address VARCHAR(255),
    city VARCHAR(255),
    state_abbre VARCHAR(255),
    state VARCHAR(255),
    zipcode INT);

CREATE TABLE pets 
    (pet_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    kind VARCHAR(255),
    gender VARCHAR(255),
    age INT,
    owner_id INT,
    FOREIGN KEY (owner_id) REFERENCES owners(owner_id));

CREATE TABLE procedure_details 
    (procedure_type VARCHAR(255),
    procedure_subcode INT,
    description VARCHAR(255),
    PRIMARY KEY (procedure_type, procedure_subcode));

CREATE TABLE procedure_history 
    (pet_id VARCHAR(255),
    procedure_date DATE,
    procedure_type VARCHAR(255),
    procedure_subcode INT,
    PRIMARY KEY (pet_id, procedure_date),
    FOREIGN KEY (pet_id) REFERENCES pets(pet_id),
    FOREIGN KEY (procedure_type, procedure_subcode) REFERENCES procedure_details(procedure_type, procedure_subcode));

ALTER TABLE procedure_details
ADD price int;