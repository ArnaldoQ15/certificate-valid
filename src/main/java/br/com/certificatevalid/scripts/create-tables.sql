CREATE TABLE company (
        company_id SERIAL PRIMARY KEY,
        company_name VARCHAR(255) NOT NULL,
        contact_email VARCHAR(255) NOT NULL,
        company_verification_code VARCHAR(10),
        count_user INT,
        company_password VARCHAR(255)
);

CREATE TABLE user_system (
        user_id SERIAL PRIMARY KEY,
        username VARCHAR(255) NOT NULL,
        password VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL,
        document_cpf VARCHAR(255),
        data_status VARCHAR(30),
        company_id SERIAL NULL,
        FOREIGN KEY (company_id) REFERENCES company(company_id)
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

CREATE TABLE course (
        course_id SERIAL PRIMARY KEY,
        title VARCHAR(255) NOT NULL,
        description VARCHAR(255) NOT NULL,
        course_verification_code VARCHAR(255) NOT NULL,
        company_id SERIAL NOT NULL,
        data_status VARCHAR(255) NOT NULL,
        finish_date TIMESTAMP NOT NULL,
        FOREIGN KEY (company_id) REFERENCES company(company_id)
);