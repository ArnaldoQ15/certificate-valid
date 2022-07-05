CREATE TABLE company (
        company_id SERIAL PRIMARY KEY,
        company_name VARCHAR(255) NOT NULL,
        contact_email VARCHAR(255) NOT NULL,
        company_verification_code VARCHAR(10),
        count_user INT
);

CREATE TABLE user_system (
        user_id SERIAL PRIMARY KEY,
        username VARCHAR(255) NOT NULL,
        password VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL,
        document_cpf VARCHAR(255),
        company_id SERIAL NULL,
);

CREATE TABLE verification_code (
        code_id SERIAL PRIMARY KEY,
        first_field VARCHAR(10),
        second_field VARCHAR(10),
        third_field VARCHAR(10),
        fourth_field VARCHAR(10),
        firth_field VARCHAR(10),
        full_field VARCHAR(50),
        appointment_id SERIAL
);

ALTER TABLE user_system
        ADD CONSTRAINT fk_company_id
        FOREIGN KEY (company_id)
        REFERENCES company;