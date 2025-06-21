-- Insert products with new image structure
INSERT INTO products (name, brand, product_condition, category, warranty, description, price, original_price, rating, reviews, image_url) VALUES
-- Business Laptops
('Dell Latitude 5410', 'Dell', 'Excellent', 'Business', '1 Year Warranty', 'Professional business laptop with Intel i7 processor, 16GB RAM, and 512GB SSD. Perfect for corporate use.', 899.99, 1099.99, 4.5, 127, 'dell_latitude_5410.jpg'),
('HP EliteBook 840', 'HP', 'Good', 'Business', '1 Year Warranty', 'Reliable business laptop with Intel i5 processor, 8GB RAM, and 256GB SSD. Ideal for office work.', 749.99, 899.99, 4.3, 89, 'hp_elitebook_840.jpg'),
('Lenovo ThinkPad X1', 'Lenovo', 'Excellent', 'Business', '2 Year Warranty', 'Premium business laptop with Intel i7 processor, 16GB RAM, and 1TB SSD. Ultra-lightweight design.', 1299.99, 1499.99, 4.7, 156, 'lenovo_thinkpad_x1.jpg'),
('Asus ExpertBook', 'Asus', 'Good', 'Business', '1 Year Warranty', 'Professional laptop with AMD Ryzen 7 processor, 16GB RAM, and 512GB SSD. Great for multitasking.', 799.99, 949.99, 4.2, 73, 'asus_expertbook.jpg'),
('Acer TravelMate', 'Acer', 'Fair', 'Business', '1 Year Warranty', 'Affordable business laptop with Intel i5 processor, 8GB RAM, and 256GB SSD. Good value for money.', 599.99, 699.99, 4.0, 45, 'acer_travelmate.jpg'),

-- Gaming Laptops
('MSI GF65 Thin', 'MSI', 'Excellent', 'Gaming', '2 Year Warranty', 'Gaming laptop with RTX 3060 graphics, Intel i7 processor, 16GB RAM, and 512GB SSD. Perfect for gaming.', 1199.99, 1399.99, 4.6, 203, 'msi_gf65_thin.jpg'),
('Dell G5 15', 'Dell', 'Good', 'Gaming', '1 Year Warranty', 'Gaming laptop with GTX 1660 Ti graphics, Intel i5 processor, 8GB RAM, and 256GB SSD. Great for casual gaming.', 899.99, 1099.99, 4.4, 167, 'dell_g5_15.jpg'),

-- Ultrabooks
('HP Envy 13', 'HP', 'Excellent', 'Ultrabooks', '1 Year Warranty', 'Ultra-thin laptop with Intel i7 processor, 16GB RAM, and 512GB SSD. Premium design and performance.', 999.99, 1199.99, 4.5, 134, 'hp_envy_13.jpg'),
('Dell XPS 13', 'Dell', 'Excellent', 'Ultrabooks', '2 Year Warranty', 'Premium ultrabook with Intel i7 processor, 16GB RAM, and 1TB SSD. InfinityEdge display.', 1299.99, 1499.99, 4.8, 189, 'dell_xps_13.jpg'),

-- Budget Laptops
('Acer Aspire 3', 'Acer', 'Good', 'Budget', '1 Year Warranty', 'Affordable laptop with AMD Ryzen 5 processor, 8GB RAM, and 256GB SSD. Perfect for students.', 449.99, 549.99, 4.1, 98, 'acer_aspire_3.jpg'),
('Lenovo IdeaPad 3', 'Lenovo', 'Fair', 'Budget', '1 Year Warranty', 'Budget-friendly laptop with Intel i3 processor, 4GB RAM, and 128GB SSD. Basic computing needs.', 349.99, 449.99, 3.9, 67, 'lenovo_ideapad_3.jpg');

-- Update product_images table with new structure
INSERT INTO product_images (category, image_path, image_name) VALUES
-- Business Laptops
('business', 'uploads/images/laptops/business/dell_latitude_5410.jpg', 'dell_latitude_5410.jpg'),
('business', 'uploads/images/laptops/business/hp_elitebook_840.jpg', 'hp_elitebook_840.jpg'),
('business', 'uploads/images/laptops/business/lenovo_thinkpad_x1.jpg', 'lenovo_thinkpad_x1.jpg'),
('business', 'uploads/images/laptops/business/asus_expertbook.jpg', 'asus_expertbook.jpg'),
('business', 'uploads/images/laptops/business/acer_travelmate.jpg', 'acer_travelmate.jpg'),

-- Gaming Laptops
('gaming', 'uploads/images/laptops/gaming/msi_gf65_thin.jpg', 'msi_gf65_thin.jpg'),
('gaming', 'uploads/images/laptops/gaming/dell_g5_15.jpg', 'dell_g5_15.jpg'),

-- Ultrabooks
('ultrabooks', 'uploads/images/laptops/ultrabooks/hp_envy_13.jpg', 'hp_envy_13.jpg'),
('ultrabooks', 'uploads/images/laptops/ultrabooks/dell_xps_13.jpg', 'dell_xps_13.jpg'),

-- Budget Laptops
('budget', 'uploads/images/laptops/budget/acer_aspire_3.jpg', 'acer_aspire_3.jpg'),
('budget', 'uploads/images/laptops/budget/lenovo_ideapad_3.jpg', 'lenovo_ideapad_3.jpg'),

-- Other categories
('hero', 'uploads/images/hero/image_1.jpg', 'image_1.jpg'),
('about', 'uploads/images/about/image_1.jpg', 'image_1.jpg'),
('testimonials', 'uploads/images/testimonials/image_1.jpg', 'image_1.jpg'),
('testimonials', 'uploads/images/testimonials/image_2.jpg', 'image_2.jpg'),
('testimonials', 'uploads/images/testimonials/image_3.jpg', 'image_3.jpg'); 