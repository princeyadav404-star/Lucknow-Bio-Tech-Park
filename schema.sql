-- SQL schema for Lucknow Biotech Park
CREATE DATABASE IF NOT EXISTS biotech_park CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE biotech_park;

CREATE TABLE IF NOT EXISTS projects (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  description TEXT,
  lead_scientist VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS scientists (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  specialization VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS labs (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  equipment TEXT
);

-- sample data
INSERT INTO scientists (name, specialization) VALUES
('Dr. A. K. Singh', 'Genomics'),
('Dr. S. Verma', 'Bioinformatics');

INSERT INTO labs (name, equipment) VALUES
('Genomics Lab', 'Sequencers, PCR, Freezers'),
('Bioinformatics Lab', 'Compute cluster, GPUs');

INSERT INTO projects (name, description, lead_scientist) VALUES
('Crop Resilience Study', 'Study on stress-resistance in rice', 'Dr. A. K. Singh'),
('Protein Folding Simulation', 'AI-driven folding simulation', 'Dr. S. Verma');
