---- Base Accounts
INSERT INTO account (account_id, account_number) VALUES (101, 'EXT-ACC-001');
INSERT INTO account (account_id, account_number) VALUES (102, 'INT-ACC-001');
--
---- Subclass tables (JOINED inheritance)
INSERT INTO external_account (account_id) VALUES (101);
INSERT INTO internal_account (account_id) VALUES (102);

-- Transactions
INSERT INTO transactions (
    transaction_id,
    transaction_reference_id,
    transaction_start_time,
    transaction_end_time,
    transaction_type,
    transaction_status,
    amount_deduction_time,
    amount_addition_time,
    sender_account_id,
    receiver_account_id,
    transaction_currency_code,
    amount,
    fees,
    net_amount,
    channel,
    created_by,
    updated_by,
    payload_hash
) VALUES
(201, 'TXN-10001', '2025-12-15T09:30:00Z', '2025-12-15T09:31:30Z',
 'TRANSFER', 'COMPLETED', '2025-12-15T09:30:05Z', '2025-12-15T09:31:25Z',
 101, 102, 'USD', 2500.75, 25.00, 2475.75, 'ONLINE', 'system', 'system',
 'a94a8fe5ccb19ba61c4c0873d391e987982fbbd3'),

(202, 'TXN-10002', '2025-12-15T10:15:00Z', '2025-12-15T10:16:00Z',
 'WITHDRAWAL', 'FAILED', '2025-12-15T10:15:10Z', NULL,
 101, 102, 'EUR', 500.00, 5.00, 495.00, 'ATM', 'system', 'system',
 '0cc175b9c0f1b6a831c399e269772661'),

(203, 'TXN-10003', '2025-12-15T11:45:00Z', '2025-12-15T11:46:00Z',
 'DEPOSIT', 'PENDING', NULL, '2025-12-15T11:45:30Z',
 102, 101, 'GBP', 1200.00, 12.00, 1188.00, 'BRANCH', 'system', 'system',
 '900150983cd24fb0d6963f7d28e17f72'),

(204, 'TXN-10004', '2025-12-15T12:30:00Z', '2025-12-15T12:31:00Z',
 'TRANSFER', 'COMPLETED', '2025-12-15T12:30:05Z', '2025-12-15T12:30:55Z',
 101, 102, 'USD', 750.00, 7.50, 742.50, 'MOBILE', 'system', 'system',
 'c3fcd3d76192e4007dfb496cca67e13b'),

(205, 'TXN-10005', '2025-12-15T13:45:00Z', '2025-12-15T13:46:00Z',
 'DEPOSIT', 'COMPLETED', NULL, '2025-12-15T13:45:30Z',
 102, 101, 'INR', 300.00, 3.00, 297.00, 'ONLINE', 'system', 'system',
 'cfcd208495d565ef66e7dff9f98764da');