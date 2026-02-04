INSERT INTO roles (rol_name) VALUES
('ADMIN'),
('USER');

INSERT INTO courses (course_name, course_score) VALUES
('DAM', 0),
('DAW', 0);

INSERT INTO categories (category_name) VALUES
('Historia'),
('Ciencia'),
('Deporte'),
('Videojuegos');

INSERT INTO users (
  email,
  password,
  username,
  name,
  surname1,
  surname2,
  roles_rol_id,
  courses_course_id
) VALUES
(
  'admin@trivuna.com',
  '$2a$10$X7L0Z7hxnxggYPPUrk.LOOp/iA5wdH4MVQSCybg4sCZuj1k10wj56',
  'admin',
  'Admin',
  'Root',
  NULL,
  1,
  2
),
(
  'user@trivuna.com',
  '$2a$10$Dow1Q9rPqQ8K0xkZyG8nUe7bP1qYxQk5Z6mQX5lZ6X5Rk5G5K5G5K',
  'user1',
  'Juan',
  'Pérez',
  'García',
  2,
  1
);

INSERT INTO questions (
  question_text,
  incorrect_answer1,
  incorrect_answer2,
  incorrect_answer3,
  respuesta_correcta,
  is_approved,
  categories_category_id,
  users_user_id
) VALUES
(
  '¿En qué año comenzó la Segunda Guerra Mundial?',
  '1936',
  '1941',
  '1945',
  '1939',
  1,
  1,
  1
),
(
  '¿Cuál es el planeta más grande del sistema solar?',
  'Marte',
  'La Tierra',
  'Saturno',
  'Júpiter',
  1,
  2,
  2
),
(
  '¿Qué videojuego es conocido por el personaje Link?',
  'Mario Bros',
  'Final Fantasy',
  'Halo',
  'The Legend of Zelda',
  0,
  4,
  2
);

INSERT INTO users_answers_questions (
  users_user_id,
  questions_question_id,
  selected_answer
) VALUES
(2, 1, '1939'),
(2, 2, 'Júpiter'),
(2, 3, 'Mario Bros');
