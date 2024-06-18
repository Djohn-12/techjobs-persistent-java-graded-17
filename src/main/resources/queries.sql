-- Part 1: List columns and data types in the job table
SHOW COLUMNS FROM job;

-- Part 2: List the names of the employers in St. Louis City
SELECT name FROM employer WHERE location = 'St. Louis City';

-- Part 3: Remove the job table
DROP TABLE IF EXISTS job;

-- Part 4: Return the names of all skills attached to jobs in alphabetical order
SELECT DISTINCT s.name
FROM skill s
JOIN job_skill js ON s.id = js.skill_id
ORDER BY s.name;