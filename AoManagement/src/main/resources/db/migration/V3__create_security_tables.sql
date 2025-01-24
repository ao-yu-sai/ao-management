-- ユーザーテーブル
CREATE TABLE users (
    user_id serial4 NOT NULL,
    username varchar(100) NOT NULL,
    password varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    is_active boolean DEFAULT true,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (user_id),
    CONSTRAINT users_username_key UNIQUE (username),
    CONSTRAINT users_email_key UNIQUE (email)
);

COMMENT ON TABLE users IS 'ユーザー';
COMMENT ON COLUMN users.user_id IS 'ユーザーID';
COMMENT ON COLUMN users.username IS 'ユーザー名';
COMMENT ON COLUMN users.password IS 'パスワード';
COMMENT ON COLUMN users.email IS 'メールアドレス';
COMMENT ON COLUMN users.is_active IS '有効フラグ';

-- ロールテーブル
CREATE TABLE roles (
    role_id serial4 NOT NULL,
    role_name varchar(50) NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (role_id),
    CONSTRAINT roles_role_name_key UNIQUE (role_name)
);

COMMENT ON TABLE roles IS 'ロール';

-- ユーザーロール関連テーブル
CREATE TABLE user_roles (
    user_id int4 NOT NULL,
    role_id int4 NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

COMMENT ON TABLE user_roles IS 'ユーザーロール関連';

-- 初期データ投入
-- ロール
INSERT INTO roles (role_name) VALUES 
('ROLE_ADMIN'),    -- システム管理者
('ROLE_USER');     -- 一般ユーザー

-- パスワードは実際に生成したハッシュ値に置き換える
INSERT INTO users (username, password, email) VALUES 
('admin', '生成したadminのパスワードハッシュ', 'admin@example.com'),
('user', '生成したuserのパスワードハッシュ', 'user@example.com');

-- ユーザーロール割り当て
INSERT INTO user_roles (user_id, role_id) 
SELECT u.user_id, r.role_id 
FROM users u, roles r 
WHERE u.username = 'admin' AND r.role_name = 'ROLE_ADMIN';

INSERT INTO user_roles (user_id, role_id) 
SELECT u.user_id, r.role_id 
FROM users u, roles r 
WHERE u.username = 'user' AND r.role_name = 'ROLE_USER'; 