insert into account_types (account_type) values ('applicant');
insert into account_types (account_type) values ('employer');
insert into account_types (account_type) values ('admin');

insert into category (id, name, category_id) values (1, 'IT', NULL);
insert into category (id, name, category_id) values (2, 'бэкенд разработка', 1);
insert into category (id, name, category_id) values (3, 'фронтенд разработка', 1);
insert into category (id, name, category_id) values (4, 'ресторанный бизнес', NULL);
insert into category (id, name, category_id) values (5, 'охрана', NULL);

insert into contact_types (type) values ('телеграмм');
insert into contact_types (type) values ('WhatsApp');
insert into contact_types (type) values ('VK');
insert into contact_types (type) values ('Skype');
insert into contact_types (type) values ('LinkedIn');

--пароль qwe у всех
insert into users (name, surname, age, email, password, phone_number, avatar, account_type, enabled)
values ('TechBit', '', 35, 'tech@a.ru', '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', '+79991234567', '/avatars/techbit.jpg', 2, true);
insert into users (name, surname, age, email, password, phone_number, avatar, account_type, enabled)
values ('Анна', 'Смирнова', 28, 'anna@a.ru', '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', '+79997654321', '/avatars/anna.jpg', 1, true);
insert into users (name, surname, age, email, password, phone_number, avatar, account_type, enabled)
values ('admin', '', 35, 'admin@a.ru', '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', '+79991234567', '/avatars/admin.jpg', 3, true);

insert into vacancies (name, description, category_id, salary, experience_min, experience_max, is_active, author_id, created_time, updated_time)
values ('Бэкенд разработчик (Java)', 'Ищем опытного бэкенд разработчика для работы над проектами компании. Требуется знание Java, Spring, и баз данных.', 2, 150000.00, 3, 5, true, 1, '2025-03-20 10:00:00', '2025-03-20 10:00:00');
insert into vacancies (name, description, category_id, salary, experience_min, experience_max, is_active, author_id, created_time, updated_time)
values ('Фронтенд разработчик (React)', 'Требуется фронтенд разработчик для создания пользовательских интерфейсов. Необходим опыт работы с React и JavaScript.', 3, 120000.00, 2, 4, true, 1, '2025-03-20 11:00:00', '2025-03-20 11:00:00');

insert into resumes (applicant_id, name, category_id, salary, is_active, created_time, updated_time)
values (2, 'Бэкенд разработчик (Java, Spring)', 2, 140000.00, true, '2025-03-20 12:00:00', '2025-03-20 12:00:00');
insert into resumes (applicant_id, name, category_id, salary, is_active, created_time, updated_time)
values (2, 'Фронтенд разработчик (React, JavaScript)', 3, 110000.00, true, '2025-03-20 12:30:00', '2025-03-20 12:30:00');

insert into contacts (type_id, resume_id, contact_value)
values (1, 1, '@anna_backend');
insert into contacts (type_id, resume_id, contact_value)
values (2, 1, '+79997654321');
insert into contacts (type_id, resume_id, contact_value)
values (1, 2, '@anna_frontend');
insert into contacts (type_id, resume_id, contact_value)
values (2, 2, '+79997654321');

insert into educations (resume_id, institution, program, start_date, end_date, degree)
values (1, 'Московский государственный университет', 'Информатика и вычислительная техника', '2015-09-01', '2019-06-30', 'Бакалавр');
insert into educations (resume_id, institution, program, start_date, end_date, degree)
values (2, 'Московский государственный университет', 'Информатика и вычислительная техника', '2015-09-01', '2019-06-30', 'Бакалавр');

insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values (1, 4, 'SoftSolutions', 'Бэкенд разработчик', 'Разработка серверной части приложений на Java и Spring, работа с базами данных MySQL, оптимизация запросов.');
insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values (2, 3, 'WebCrafters', 'Фронтенд разработчик', 'Создание пользовательских интерфейсов с использованием React и JavaScript, интеграция с API, обеспечение кроссбраузерной совместимости.');

insert into responded_applicants (resume_id, vacancy_id, confirmation)
values (1, 1, false);

