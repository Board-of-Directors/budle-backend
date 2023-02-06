INSERT INTO workers (user_id, worker_type, status)
VALUES (1, '1', 'absent');
INSERT INTO workers (user_id, worker_type, status)
VALUES (2, '1', 'absent');
INSERT INTO workers (user_id, worker_type, status)
VALUES (11, '2', 'absent');

INSERT INTO worker_establishments(worker_id, establishment_id)
VALUES (1, 1);
INSERT INTO worker_establishments(worker_id, establishment_id)
VALUES (2, 2);
INSERT INTO worker_establishments(worker_id, establishment_id)
VALUES (3, 2);