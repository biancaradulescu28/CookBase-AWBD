insert into app_user(id, username, email, password, role) values (1, 'bianca28', 'bianca@gmail.com', '123456', 'utilizator');

insert into user_profile(id, full_name, date_of_birth, bio, profile_picture_url, user_id) values (1, 'Radulescu Bianca', '2002-09-28', 'Utilizator', 'poza', 1);

insert into recipe(id, title, description, prep_time_in_minutes, cook_time_in_minutes, servings, difficulty, author_id) values(1, 'Paste Carbonara', 'Creamy pasta carbonara is as easy as it is decadent! Using our foolproof method, the eggs are whisked with Parmesan cheese, then tossed with the pasta off the heat for a thick, creamy sauce that will not overcook. Finish with crispy chopped bacon and a sprinkling of freshly ground black pepper for a bit of savory spice in every bite.', 20, 10, 4, 'Easy', 1);
insert into recipe(id, title, description, prep_time_in_minutes, cook_time_in_minutes, servings, difficulty, author_id) values(2, 'Paste Carbonara', 'Creamy pasta carbonara is as easy as it is decadent! Using our foolproof method, the eggs are whisked with Parmesan cheese, then tossed with the pasta off the heat for a thick, creamy sauce that will not overcook. Finish with crispy chopped bacon and a sprinkling of freshly ground black pepper for a bit of savory spice in every bite.', 20, 10, 4, 'Easy', 1);
insert into category(id, name) values(1, 'salad');
insert into category(id, name) values(2, 'pasta');
insert into category(id, name) values(3, 'chicken');

insert into ingredient(id, name, quantity, recipe_id) values(1, 'eggs', '2', 1);
insert into ingredient(id, name, quantity, recipe_id) values(2, 'egg yolks', '3', 1);
insert into ingredient(id, name, quantity, recipe_id) values(3, 'grated parmesan cheese', '125g', 1);
insert into ingredient(id, name, quantity, recipe_id) values(4, 'grated pecorino romano', '60g', 1);
insert into ingredient(id, name, quantity, recipe_id) values(5, 'salt', '1 teaspoon', 1);
insert into ingredient(id, name, quantity, recipe_id) values(6, 'black pepper', '1 teaspoon', 1);
insert into ingredient(id, name, quantity, recipe_id) values(7, 'spaghetti', '450g', 1);
insert into ingredient(id, name, quantity, recipe_id) values(8, 'bacon', '110g', 1);

insert into step (id, step_number, instruction, recipe_id) values (1, 1, 'In a medium bowl, whisk together the eggs, egg yolks, Parmesan, Pecorino Romano, salt, and pepper.', 1);
insert into step (id, step_number, instruction, recipe_id) values (2, 2, 'Bring a large pot of salted water to a boil over high heat. Add the pasta and cook until al dente according to the package instructions. Reserve ½ cup of pasta water, then drain the pasta through a colander.', 1);
insert into step (id, step_number, instruction, recipe_id) values (3, 3, 'While the pasta is cooking, add the bacon to a large pan over medium-high heat and cook, stirring occasionally, until crispy, about 8 minutes. Remove the bacon from the pan and transfer to a paper towel-lined plate. Leave the rendered bacon fat in the pan.', 1);
insert into step (id, step_number, instruction, recipe_id) values (4, 4, 'Transfer the drained pasta to the pan with the bacon fat and toss with tongs for 1 minute, until fully coated. Remove the pan from the heat and let sit for 1 minute.', 1);
insert into step (id, step_number, instruction, recipe_id) values (5, 5, 'Add 2 tablespoons of the reserved pasta water to the egg mixture and whisk to combine, then carefully pour the mixture over the pasta, quickly tossing to coat. Add the reserved pasta water, 1 tablespoon at a time, as necessary until the sauce is creamy and has the consistency of heavy cream.', 1);
insert into step (id, step_number, instruction, recipe_id) values (6, 6, 'Add the bacon and toss to combine. Serve immediately topped with more freshly ground black pepper.', 1);

insert into recipe_category (recipe_id, category_id) values(1,2);

insert into review (id, rating, comment, created_at, reviewer_id, recipe_id) values (1, 5, 'Very good', '2024-05-13T15:30:00', 1, 1);