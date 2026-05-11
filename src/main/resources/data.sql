-- 1. Insert unique ingredients into the 'ingredient' table
INSERT INTO ingredient (name) VALUES
                                  ('Chicken'), ('Rice wine'), ('Minced ginger'),
                                  ('Fine sea salt'), ('Ground black pepper'), ('Potato starch'),
                                  ('Tomato sauce'), ('Gochujang'), ('Honey'),
                                  ('Brown sugar'), ('Soy sauce'), ('Minced garlic'), ('Sesame oil');

SET @recipe_id = RANDOM_UUID();

INSERT INTO recipe (id, name, duration, portion, url)
VALUES (@recipe_id, 'Korean Fried Chicken', 40, 4, 'https://mykoreankitchen.com/korean-fried-chicken/');

SET @ri_id1 = RANDOM_UUID();
SET @ri_id2 = RANDOM_UUID();
SET @ri_id3 = RANDOM_UUID();
SET @ri_id4 = RANDOM_UUID();
SET @ri_id5 = RANDOM_UUID();
-- 3. Insert into 'receipe_ingredient' (The "Quantity" entries)
-- Note: 'amount' is an integer in your schema, so we approximate the measurements.
-- We use a series of UUIDs for these specific instances.
INSERT INTO receipe_ingredient (id, ingredient_name, amount) VALUES
                                                                 (@ri_id1, 'Chicken', 1400),
                                                                 (@ri_id2, 'Rice wine', 30),
                                                                 (@ri_id3, 'Gochujang', 45),
                                                                 (@ri_id4, 'Honey', 60),
                                                                 (@ri_id5, 'Soy sauce', 30);

-- 4. Link the Recipe to the Ingredients in the join table 'recipe_ingredients'
-- Format: (ingredients_id, recipe_id)
INSERT INTO recipe_ingredients (ingredients_id, recipe_id) VALUES
                                                               (@ri_id1, @recipe_id),
                                                               (@ri_id2, @recipe_id),
                                                               (@ri_id3, @recipe_id),
                                                               (@ri_id4, @recipe_id),
                                                               (@ri_id5, @recipe_id);