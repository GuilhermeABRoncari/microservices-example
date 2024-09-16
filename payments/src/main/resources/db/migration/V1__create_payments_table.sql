CREATE TABLE payments (
    id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(19, 2) NOT NULL CHECK (amount > 0),
    user_name VARCHAR(100) NOT NULL,
    card_number VARCHAR(19) NOT NULL,
    expiration VARCHAR(7) NOT NULL,
    cvv CHAR(3) NOT NULL,
    status VARCHAR(255) NOT NULL,
    order_id BIGINT(20) NOT NULL,
    chosen_payment_id BIGINT(20) NOT NULL
);
