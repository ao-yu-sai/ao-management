-- category definition

-- Drop table

-- DROP TABLE category;

CREATE TABLE category (
	category_id serial4 NOT NULL, -- 区分ID
	category_type_code varchar(50) NOT NULL, -- 区分種別コード
	category_code varchar(50) NOT NULL, -- 区分コード
	category_name varchar(100) NOT NULL, -- 区分名
	description text NULL, -- 説明
	display_order int4 DEFAULT 0 NULL, -- 表示順
	is_active bool DEFAULT true NULL, -- 有効フラグ
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 作成日時
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 更新日時
	CONSTRAINT category_pkey PRIMARY KEY (category_id, category_type_code, category_code)
);
CREATE INDEX idx_category_code ON category USING btree (category_code);
CREATE INDEX idx_category_name ON category USING btree (category_name);
CREATE INDEX idx_category_type_code ON category USING btree (category_type_code);
CREATE INDEX idx_category_id ON category USING btree (category_id);
COMMENT ON TABLE category IS '区分マスター';

-- Column comments

COMMENT ON COLUMN category.category_type_code IS '区分種別コード';
COMMENT ON COLUMN category.category_code IS '区分コード';
COMMENT ON COLUMN category.category_name IS '区分名';
COMMENT ON COLUMN category.description IS '説明';
COMMENT ON COLUMN category.display_order IS '表示順';
COMMENT ON COLUMN category.is_active IS '有効フラグ';
COMMENT ON COLUMN category.created_at IS '作成日時';
COMMENT ON COLUMN category.updated_at IS '更新日時';


-- category_type definition

-- Drop table

-- DROP TABLE category_type;

CREATE TABLE category_type (
	category_type_code varchar(50) NOT NULL, -- 区分種別コード
	category_type_name varchar(100) NOT NULL, -- 区分種別名
	description text NULL, -- 説明
	is_active bool DEFAULT true NULL, -- 有効フラグ
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 作成日時
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 更新日時
	CONSTRAINT category_type_pkey PRIMARY KEY (category_type_code)
);
CREATE INDEX idx_category_type_name ON category_type USING btree (category_type_name);
COMMENT ON TABLE category_type IS '区分種別マスター';

-- Column comments

COMMENT ON COLUMN category_type.category_type_code IS '区分種別コード';
COMMENT ON COLUMN category_type.category_type_name IS '区分種別名';
COMMENT ON COLUMN category_type.description IS '説明';
COMMENT ON COLUMN category_type.is_active IS '有効フラグ';
COMMENT ON COLUMN category_type.created_at IS '作成日時';
COMMENT ON COLUMN category_type.updated_at IS '更新日時';


-- daily_work definition

-- Drop table

-- DROP TABLE daily_work;

CREATE TABLE daily_work (
	daily_work_id serial4 NOT NULL, -- 実績工数ID
	task_id int4 NOT NULL, -- タスクID
	staff_id int4 NOT NULL, -- 担当者ID
	work_date date NOT NULL, -- 作業日
	actual_hours numeric(5, 2) NOT NULL, -- 実績工数
	"comment" text NULL, -- コメント
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 作成日時
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 更新日時
	CONSTRAINT daily_work_pkey PRIMARY KEY (daily_work_id)
);
CREATE INDEX idx_daily_work_date ON daily_work USING btree (work_date);
CREATE INDEX idx_daily_work_staff ON daily_work USING btree (staff_id);
CREATE INDEX idx_daily_work_task ON daily_work USING btree (task_id);
COMMENT ON TABLE daily_work IS '日別実績工数';

-- Column comments

COMMENT ON COLUMN daily_work.daily_work_id IS '実績工数ID';
COMMENT ON COLUMN daily_work.task_id IS 'タスクID';
COMMENT ON COLUMN daily_work.staff_id IS '担当者ID';
COMMENT ON COLUMN daily_work.work_date IS '作業日';
COMMENT ON COLUMN daily_work.actual_hours IS '実績工数';
COMMENT ON COLUMN daily_work."comment" IS 'コメント';
COMMENT ON COLUMN daily_work.created_at IS '作成日時';
COMMENT ON COLUMN daily_work.updated_at IS '更新日時';


-- feature definition

-- Drop table

-- DROP TABLE feature;

CREATE TABLE feature (
	feature_id serial4 NOT NULL, -- 機能ID
	project_id int4 NOT NULL, -- 案件ID
	feature_name varchar(200) NOT NULL, -- 機能名
	description text NOT NULL, -- 説明
	progress_rate numeric(5, 2) NOT NULL, -- 進捗率
	status varchar(50) NOT NULL, -- ステータス
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 作成日時
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 更新日時
	CONSTRAINT feature_pkey PRIMARY KEY (feature_id)
);
CREATE INDEX idx_feature_project ON feature USING btree (project_id);
COMMENT ON TABLE feature IS '機能情報';

-- Column comments

COMMENT ON COLUMN feature.feature_id IS '機能ID';
COMMENT ON COLUMN feature.project_id IS '案件ID';
COMMENT ON COLUMN feature.feature_name IS '機能名';
COMMENT ON COLUMN feature.description IS '説明';
COMMENT ON COLUMN feature.progress_rate IS '進捗率';
COMMENT ON COLUMN feature.status IS 'ステータス';
COMMENT ON COLUMN feature.created_at IS '作成日時';
COMMENT ON COLUMN feature.updated_at IS '更新日時';


-- flyway_schema_history definition

-- Drop table

-- DROP TABLE flyway_schema_history;

CREATE TABLE flyway_schema_history (
	installed_rank int4 NOT NULL,
	"version" varchar(50) NULL,
	description varchar(200) NOT NULL,
	"type" varchar(20) NOT NULL,
	script varchar(1000) NOT NULL,
	checksum int4 NULL,
	installed_by varchar(100) NOT NULL,
	installed_on timestamp DEFAULT now() NOT NULL,
	execution_time int4 NOT NULL,
	success bool NOT NULL,
	CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank)
);
CREATE INDEX flyway_schema_history_s_idx ON flyway_schema_history USING btree (success);


-- function_info definition

-- Drop table

-- DROP TABLE function_info;

CREATE TABLE function_info (
	service_kbn_code varchar(50) NOT NULL, -- サービス区分コード
	function_code varchar(50) NOT NULL, -- 機能コード
	function_name varchar(100) NOT NULL, -- 機能名
	description text NULL, -- 説明
	is_active bool DEFAULT true NULL, -- 有効フラグ
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 作成日時
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 更新日時
	CONSTRAINT function_info_pkey PRIMARY KEY (service_kbn_code, function_code)
);
CREATE INDEX idx_function_code ON function_info USING btree (function_code);
CREATE INDEX idx_service_kbn_code ON function_info USING btree (service_kbn_code);
COMMENT ON TABLE function_info IS '機能情報マスター';

-- Column comments

COMMENT ON COLUMN function_info.service_kbn_code IS 'サービス区分コード';
COMMENT ON COLUMN function_info.function_code IS '機能コード';
COMMENT ON COLUMN function_info.function_name IS '機能名';
COMMENT ON COLUMN function_info.description IS '説明';
COMMENT ON COLUMN function_info.is_active IS '有効フラグ';
COMMENT ON COLUMN function_info.created_at IS '作成日時';
COMMENT ON COLUMN function_info.updated_at IS '更新日時';


-- project definition

-- Drop table

-- DROP TABLE project;

CREATE TABLE project (
	ticket_number varchar(50) NOT NULL, -- チケット番号
	project_name varchar(100) NOT NULL, -- 案件名
	description text NULL, -- 説明
	service_kbn_code varchar(50) NULL, -- サービス区分コード
	status varchar(50) DEFAULT '未着手'::character varying NOT NULL, -- ステータス
	priority varchar(50) DEFAULT '中'::character varying NOT NULL, -- 優先度
	progress_rate int4 DEFAULT 0 NULL, -- 進捗率
	is_active bool DEFAULT true NULL, -- 有効フラグ
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 作成日時
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 更新日時
	CONSTRAINT project_pkey PRIMARY KEY (ticket_number)
);
CREATE INDEX idx_project_name ON project USING btree (project_name);
CREATE INDEX idx_project_service_kbn ON project USING btree (service_kbn_code);
COMMENT ON TABLE project IS '案件マスター';

-- Column comments

COMMENT ON COLUMN project.ticket_number IS 'チケット番号';
COMMENT ON COLUMN project.project_name IS '案件名';
COMMENT ON COLUMN project.description IS '説明';
COMMENT ON COLUMN project.service_kbn_code IS 'サービス区分コード';
COMMENT ON COLUMN project.status IS 'ステータス';
COMMENT ON COLUMN project.priority IS '優先度';
COMMENT ON COLUMN project.progress_rate IS '進捗率';
COMMENT ON COLUMN project.is_active IS '有効フラグ';
COMMENT ON COLUMN project.created_at IS '作成日時';
COMMENT ON COLUMN project.updated_at IS '更新日時';


-- project_function_info definition

-- Drop table

-- DROP TABLE project_function_info;

CREATE TABLE project_function_info (
	ticket_number varchar(20) NOT NULL,
	service_kbn_code varchar(20) NOT NULL,
	function_code varchar(20) NOT NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	CONSTRAINT project_function_info_pkey PRIMARY KEY (ticket_number, service_kbn_code, function_code)
);


-- project_function_task_info definition

-- Drop table

-- DROP TABLE project_function_task_info;

CREATE TABLE project_function_task_info (
	service_kbn_code varchar(20) NOT NULL, -- サービス区分コード
	ticket_number varchar(20) NOT NULL, -- チケット番号
	function_code varchar(20) NOT NULL, -- 機能コード
	task_kbn_code varchar(20) NOT NULL, -- タスク区分コード
	person_in_charge varchar(50) NOT NULL, -- 担当者
	planned_start_date date NULL, -- 開始予定日
	planned_end_date date NULL, -- 終了予定日
	planned_man_hours numeric(5, 2) NULL, -- 予定工数
	actual_start_date date NULL, -- 実績開始日
	actual_end_date date NULL, -- 実績終了日
	actual_man_hours numeric(5, 2) NULL, -- 実績工数
	progress_rate int4 NULL, -- 進捗率
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 作成日時
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 更新日時
	CONSTRAINT project_function_task_info_pkey PRIMARY KEY (service_kbn_code, ticket_number, function_code, task_kbn_code, person_in_charge)
);
COMMENT ON TABLE project_function_task_info IS '案件機能別タスク情報';

-- Column comments

COMMENT ON COLUMN project_function_task_info.service_kbn_code IS 'サービス区分コード';
COMMENT ON COLUMN project_function_task_info.ticket_number IS 'チケット番号';
COMMENT ON COLUMN project_function_task_info.function_code IS '機能コード';
COMMENT ON COLUMN project_function_task_info.task_kbn_code IS 'タスク区分コード';
COMMENT ON COLUMN project_function_task_info.person_in_charge IS '担当者';
COMMENT ON COLUMN project_function_task_info.planned_start_date IS '開始予定日';
COMMENT ON COLUMN project_function_task_info.planned_end_date IS '終了予定日';
COMMENT ON COLUMN project_function_task_info.planned_man_hours IS '予定工数';
COMMENT ON COLUMN project_function_task_info.actual_start_date IS '実績開始日';
COMMENT ON COLUMN project_function_task_info.actual_end_date IS '実績終了日';
COMMENT ON COLUMN project_function_task_info.actual_man_hours IS '実績工数';
COMMENT ON COLUMN project_function_task_info.progress_rate IS '進捗率';
COMMENT ON COLUMN project_function_task_info.created_at IS '作成日時';
COMMENT ON COLUMN project_function_task_info.updated_at IS '更新日時';


-- staff definition

-- Drop table

-- DROP TABLE staff;

CREATE TABLE staff (
	staff_id serial4 NOT NULL, -- 担当者ID
	staff_name varchar(100) NOT NULL, -- 担当者名
	email varchar(255) NOT NULL, -- メールアドレス
	department varchar(100) NOT NULL, -- 所属部署
	is_active bool DEFAULT true NULL, -- 有効フラグ
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 作成日時
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 更新日時
	CONSTRAINT staff_email_key UNIQUE (email),
	CONSTRAINT staff_pkey PRIMARY KEY (staff_id)
);
CREATE INDEX idx_staff_email ON staff USING btree (email);
COMMENT ON TABLE staff IS '担当者マスター';

-- Column comments

COMMENT ON COLUMN staff.staff_id IS '担当者ID';
COMMENT ON COLUMN staff.staff_name IS '担当者名';
COMMENT ON COLUMN staff.email IS 'メールアドレス';
COMMENT ON COLUMN staff.department IS '所属部署';
COMMENT ON COLUMN staff.is_active IS '有効フラグ';
COMMENT ON COLUMN staff.created_at IS '作成日時';
COMMENT ON COLUMN staff.updated_at IS '更新日時';


-- task definition

-- Drop table

-- DROP TABLE task;

CREATE TABLE task (
	task_id serial4 NOT NULL, -- タスクID
	feature_id int4 NOT NULL, -- 機能ID
	category_type_id int4 NOT NULL, -- 区分種別ID
	category_id int4 NOT NULL, -- 区分ID
	task_name varchar(200) NOT NULL, -- タスク名
	description text NOT NULL, -- 説明
	planned_hours numeric(7, 2) NOT NULL, -- 予定工数
	actual_hours numeric(7, 2) NOT NULL, -- 実績工数
	progress_rate numeric(5, 2) NOT NULL, -- 進捗率
	status varchar(50) NOT NULL, -- ステータス
	start_date date NULL, -- 開始日
	end_date date NULL, -- 終了日
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 作成日時
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 更新日時
	CONSTRAINT task_pkey PRIMARY KEY (task_id)
);
CREATE INDEX idx_task_category ON task USING btree (category_id);
CREATE INDEX idx_task_feature ON task USING btree (feature_id);
COMMENT ON TABLE task IS 'タスク情報';

-- Column comments

COMMENT ON COLUMN task.task_id IS 'タスクID';
COMMENT ON COLUMN task.feature_id IS '機能ID';
COMMENT ON COLUMN task.category_type_id IS '区分種別ID';
COMMENT ON COLUMN task.category_id IS '区分ID';
COMMENT ON COLUMN task.task_name IS 'タスク名';
COMMENT ON COLUMN task.description IS '説明';
COMMENT ON COLUMN task.planned_hours IS '予定工数';
COMMENT ON COLUMN task.actual_hours IS '実績工数';
COMMENT ON COLUMN task.progress_rate IS '進捗率';
COMMENT ON COLUMN task.status IS 'ステータス';
COMMENT ON COLUMN task.start_date IS '開始日';
COMMENT ON COLUMN task.end_date IS '終了日';
COMMENT ON COLUMN task.created_at IS '作成日時';
COMMENT ON COLUMN task.updated_at IS '更新日時';


-- task_staff definition

-- Drop table

-- DROP TABLE task_staff;

CREATE TABLE task_staff (
	task_id int4 NOT NULL, -- タスクID
	staff_id int4 NOT NULL, -- 担当者ID
	assigned_date date NOT NULL, -- アサイン日
	end_date date NULL, -- 終了日
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 作成日時
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 更新日時
	CONSTRAINT task_staff_pkey PRIMARY KEY (task_id, staff_id)
);
CREATE INDEX idx_task_staff_staff ON task_staff USING btree (staff_id);
COMMENT ON TABLE task_staff IS 'タスク担当者';

-- Column comments

COMMENT ON COLUMN task_staff.task_id IS 'タスクID';
COMMENT ON COLUMN task_staff.staff_id IS '担当者ID';
COMMENT ON COLUMN task_staff.assigned_date IS 'アサイン日';
COMMENT ON COLUMN task_staff.end_date IS '終了日';
COMMENT ON COLUMN task_staff.created_at IS '作成日時';
COMMENT ON COLUMN task_staff.updated_at IS '更新日時';