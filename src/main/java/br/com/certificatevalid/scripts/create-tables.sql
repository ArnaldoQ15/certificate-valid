CREATE TABLE company (
        company_id SERIAL PRIMARY KEY,
        company_name VARCHAR(255) NOT NULL,
        contact_email VARCHAR(255) NOT NULL,
        company_verification_code VARCHAR(10),
        count_user INTEGER,
        company_password VARCHAR(255)
);

CREATE TABLE user_system (
        user_id SERIAL PRIMARY KEY,
        username VARCHAR(255) NOT NULL,
        password VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL,
        document_cpf VARCHAR(255),
        data_status VARCHAR(30),
        company_id INTEGER,
        user_verification_code VARCHAR(20) NOT NULL,
        FOREIGN KEY (company_id) REFERENCES company(company_id)
);

CREATE TABLE verification_code (
        code_id SERIAL PRIMARY KEY,
        first_field VARCHAR(10) NOT NULL,
        second_field VARCHAR(20) NOT NULL,
        third_field VARCHAR(10) NOT NULL,
        fourth_field VARCHAR(10) NOT NULL,
        firth_field VARCHAR(10) NOT NULL,
        full_field VARCHAR(60) NOT NULL
);

CREATE TABLE course (
        course_id SERIAL PRIMARY KEY,
        title VARCHAR(255) NOT NULL,
        description VARCHAR(255) NOT NULL,
        course_verification_code VARCHAR(255) NOT NULL,
        company_id INTEGER NOT NULL,
        data_status VARCHAR(255) NOT NULL,
        finish_date TIMESTAMP NOT NULL,
        FOREIGN KEY (company_id) REFERENCES company(company_id)
);

CREATE TABLE certificate (
        certificate_id SERIAL PRIMARY KEY,
        course_id INTEGER NOT NULL,
        user_id INTEGER NOT NULL,
        verification_code_id INTEGER NOT NULL,
        FOREIGN KEY (course_id) REFERENCES course(course_id),
        FOREIGN KEY (user_id) REFERENCES user_system(user_id),
        FOREIGN KEY (verification_code_id) REFERENCES verification_code(code_id)
);

CREATE TABLE user_address (
        address_id SERIAL PRIMARY KEY,
        zip_code VARCHAR(255) NOT NULL,
        street VARCHAR(255) NOT NULL,
        number VARCHAR(255),
        complement VARCHAR(255),
        district VARCHAR(255) NOT NULL,
        state VARCHAR(2) NOT NULL,
        city VARCHAR(255) NOT NULL,
        user_id INTEGER NOT NULL,
        FOREIGN KEY (user_id) REFERENCES user_system(user_id)
);