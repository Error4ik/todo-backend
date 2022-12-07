INSERT INTO priorities (id, title, color)
VALUES ('b6a16ada-9d8a-407d-b4da-271d835156b8', 'Normal', 'green'),
       ('5ecf5888-3858-4740-8b99-96c5e0d59f96', 'Important', 'yellow'),
       ('8562f78c-8428-42d3-ac07-94e32c669605', 'Urgently', 'red');

INSERT INTO categories (id, title, completed_count, uncompleted_count)
VALUES ('c4c0efab-4050-4b65-937c-75be61f9eabc', 'Hobby', '0', '1'),
       ('3fc6f856-94c6-4875-ad68-c2ea22bd1ccd', 'Sport', '0', '2'),
       ('09ecd552-43b9-4a4d-a48a-82736fb5bae7', 'Training', '0', '2');

INSERT INTO tasks (id, title, completed, date, priority, category)
VALUES ('21c257ea-992a-4d12-a155-b52319da02c7', 'Do gymnastics', '0',
        '2022-12-26', 'b6a16ada-9d8a-407d-b4da-271d835156b8', '3fc6f856-94c6-4875-ad68-c2ea22bd1ccd'),
       ('d8b11880-a384-46a6-a1dc-43265277d8f8', 'Read the book', '0',
        '2022-11-27', '5ecf5888-3858-4740-8b99-96c5e0d59f96', '09ecd552-43b9-4a4d-a48a-82736fb5bae7' ),
       ('243cad10-186a-4a87-af08-2fe188e3be12', 'Watch a movie', '0',
        '2022-12-26', '5ecf5888-3858-4740-8b99-96c5e0d59f96', '09ecd552-43b9-4a4d-a48a-82736fb5bae7'),
       ('1d7f4ea2-d944-459d-a037-37243f017de4', 'Pet the cat', '0',
        '2022-12-26', '8562f78c-8428-42d3-ac07-94e32c669605', 'c4c0efab-4050-4b65-937c-75be61f9eabc'),
       ('69f42594-3f3f-47f2-944e-38f3862bf1d1', 'Training with dumbbells', '0',
        '2022-12-26', '5ecf5888-3858-4740-8b99-96c5e0d59f96', '3fc6f856-94c6-4875-ad68-c2ea22bd1ccd');

INSERT INTO statistics (id, completed_total, uncompleted_total)
VALUES ('29be9902-79fb-4d09-b993-fb31cffdd5cd', '0', '5');
