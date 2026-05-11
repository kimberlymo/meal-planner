-- 1. Tables with no foreign dependencies
CREATE TABLE ingredient (
                            name VARCHAR(255) NOT NULL,
                            PRIMARY KEY (name)
);

CREATE TABLE recipe (
                        id UUID NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        duration INTEGER NOT NULL,
                        portion INTEGER NOT NULL,
                        url VARCHAR(255),
                        PRIMARY KEY (id)
);

CREATE TABLE recurring_availability (
                                        id UUID NOT NULL,
                                        week_day TINYINT CHECK (week_day BETWEEN 0 AND 6),
                                        start_time TIME(0),
                                        end_time TIME(0),
                                        PRIMARY KEY (id)
);

CREATE TABLE specific_availability (
                                       id UUID NOT NULL,
                                       date DATE,
                                       start_time TIME(0),
                                       end_time TIME(0),
                                       is_available BOOLEAN,
                                       PRIMARY KEY (id)
);

CREATE TABLE shopping_list (
                               id UUID NOT NULL,
                               creation_date DATE,
                               PRIMARY KEY (id)
);

-- 2. Tables that depend on others (Foreign Keys)

-- Note: Keeping your 'receipe' typo to match your JPA Entity
CREATE TABLE receipe_ingredient (
                                    id UUID NOT NULL,
                                    amount INTEGER NOT NULL,
                                    ingredient_name VARCHAR(255),
                                    PRIMARY KEY (id),
                                    CONSTRAINT fk_ri_ingredient FOREIGN KEY (ingredient_name) REFERENCES ingredient(name)
);

CREATE TABLE meal (
                      id UUID NOT NULL,
                      recipe_id UUID,
                      planned_date DATE,
                      start_time TIME(0),
                      end_time TIME(0),
                      PRIMARY KEY (id),
                      CONSTRAINT fk_meal_recipe FOREIGN KEY (recipe_id) REFERENCES recipe(id)
);

-- 3. Join Tables for Many-to-Many relationships

CREATE TABLE recipe_ingredients (
                                    recipe_id UUID NOT NULL,
                                    ingredients_id UUID NOT NULL,
                                    CONSTRAINT fk_recipe_link FOREIGN KEY (recipe_id) REFERENCES recipe(id),
                                    CONSTRAINT fk_ingredient_link FOREIGN KEY (ingredients_id) REFERENCES receipe_ingredient(id)
);

CREATE TABLE shopping_list_ingredients (
                                           shopping_list_id UUID NOT NULL,
                                           ingredients_id UUID NOT NULL,
                                           CONSTRAINT fk_sl_link FOREIGN KEY (shopping_list_id) REFERENCES shopping_list(id),
                                           CONSTRAINT fk_ri_sl_link FOREIGN KEY (ingredients_id) REFERENCES receipe_ingredient(id)
);