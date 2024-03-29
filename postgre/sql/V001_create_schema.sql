CREATE SCHEMA techmaps_platform;

ALTER SCHEMA techmaps_platform OWNER TO "techmaps";

DROP TABLE IF EXISTS techmaps_platform.user CASCADE;

CREATE TABLE techmaps_platform.user(
    id uuid NOT NULL,
    username varchar NOT NULL UNIQUE,
    email varchar NOT NULL UNIQUE,
    password varchar NOT NULL,
    is_account_non_expired boolean default false,
    is_account_non_locked boolean default false,
    is_credentials_non_expired boolean default false,
    is_enabled boolean default false
);

ALTER TABLE techmaps_platform.user OWNER TO "techmaps";

ALTER TABLE techmaps_platform.user
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);

DROP TABLE IF EXISTS techmaps_platform.dashboard CASCADE;

CREATE TABLE techmaps_platform.dashboard(
    id uuid NOT NULL,
    total_roadmaps int NOT NULL,
    total_tasks int NOT NULL,
    total_commits int NOT NULL,
    total_time interval,
    user_id uuid NOT NULL
);

ALTER TABLE techmaps_platform.dashboard OWNER TO "techmaps";

ALTER TABLE techmaps_platform.dashboard
    ADD CONSTRAINT dashboard_pkey PRIMARY KEY (id);

ALTER TABLE techmaps_platform.dashboard
    ADD CONSTRAINT dashboard_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES techmaps_platform.user(id) ON DELETE CASCADE;

DROP TYPE IF EXISTS techmaps_platform.roadmap_type CASCADE;

CREATE TYPE techmaps_platform.roadmap_type AS ENUM (
    'FRONTEND',
    'BACKEND',
    'ANDROID'
);

ALTER TYPE techmaps_platform.roadmap_type OWNER TO "techmaps";

DROP TYPE IF EXISTS techmaps_platform.roadmap_lang CASCADE;

CREATE TYPE techmaps_platform.roadmap_lang AS ENUM (
    'JAVA',
    'PYTHON',
    'JAVASCRIPT',
    'KOTLIN'
);

ALTER TYPE techmaps_platform.roadmap_lang OWNER TO "techmaps";

DROP TYPE IF EXISTS techmaps_platform.roadmap_color CASCADE;

CREATE TYPE techmaps_platform.roadmap_color AS ENUM (
    'RED',
    'ORANGE',
    'BROWN',
    'YELLOW',
    'GREEN',
    'BLUE',
    'PURPLE',
    'PINK',
    'BLACK',
    'WHITE',
    'GRAY'
);

ALTER TYPE techmaps_platform.roadmap_color OWNER TO "techmaps";

DROP TABLE IF EXISTS techmaps_platform.roadmap CASCADE;

CREATE TABLE techmaps_platform.roadmap(
    id uuid NOT NULL,
    title varchar(255) NOT NULL,
    type techmaps_platform.roadmap_type,
    is_completed boolean NOT NULL,
    lang techmaps_platform.roadmap_lang,
    color techmaps_platform.roadmap_color,
    start_time timestamp NOT NULL,
    finish_time timestamp,
    total_time interval,
    commit_counter integer,
    dashboard_id uuid NOT NULL
);

ALTER TABLE techmaps_platform.roadmap OWNER TO "techmaps";

ALTER TABLE techmaps_platform.roadmap
    ADD CONSTRAINT roadmap_pkey PRIMARY KEY (id);

ALTER TABLE techmaps_platform.roadmap
    ADD CONSTRAINT roadmap_dashboard_id_fkey FOREIGN KEY (dashboard_id)
        REFERENCES techmaps_platform.dashboard(id) ON DELETE CASCADE;

DROP TYPE IF EXISTS techmaps_platform.stage_theme CASCADE;

CREATE TYPE techmaps_platform.stage_theme AS ENUM (
    'LEARN_PYTHON',
    'LEARN_JAVA',
    'LEARN_KOTLIN',
    'LEARN_HTML',
    'LEARN_CSS',
    'LEARN_JS',
    'LEARN_INTERNET',
    'LEARN_DIAGRAMS',
    'LEARN_WEBSERVERS',
    'LEARN_CLOUD',
    'LEARN_GIT',
    'LEARN_GITHUB',
    'LEARN_API',
    'LEARN_REST',
    'LEARN_SOAP',
    'LEARN_AGILE',
    'LEARN_DEVOPS',
    'LEARN_OOP',
    'LEARN_SOLID',
    'LEARN_CLEAN_CODE',
    'LEARN_TDD',
    'LEARN_CLEAN_ARCHITECTURE',
    'LEARN_VSCODE',
    'LEARN_INTELLIJ',
    'LEARN_ANDROID',
    'LEARN_NODEJS',
    'LEARN_ANGULAR',
    'LEARN_REACT',
    'LEARN_SPRING',
    'LEARN_DJANGO',
    'LEARN_BOOTSTRAP',
    'LEARN_SQL',
    'LEARN_POSTGRES',
    'LEARN_DOCKER',
    'LEARN_FIREBASE'
);

ALTER TYPE techmaps_platform.stage_theme OWNER TO "techmaps";

DROP TABLE IF EXISTS techmaps_platform.stage CASCADE;

CREATE TABLE techmaps_platform.stage(
    id uuid NOT NULL,
    roadmap_id uuid NOT NULL,
    theme techmaps_platform.stage_theme,
    is_done boolean NOT NULL,
    stage_number int NOT NULL,
    commit_counter integer
);

ALTER TABLE techmaps_platform.stage OWNER TO "techmaps";

ALTER TABLE techmaps_platform.stage
    ADD CONSTRAINT stage_pkey PRIMARY KEY (id);

ALTER TABLE techmaps_platform.stage
    ADD CONSTRAINT stage_roadmap_id_fkey FOREIGN KEY (roadmap_id)
        REFERENCES techmaps_platform.roadmap(id) ON DELETE CASCADE;

DROP TYPE IF EXISTS techmaps_platform.task_body CASCADE;

CREATE TYPE techmaps_platform.task_body AS ENUM (
    'PY1', 'PY2', 'PY3', 'PY4', 'PY5', 'PY6',
    'JV1', 'JV2', 'JV3', 'JV4', 'JV5', 'JV6', 'JV7', 'JV8',
    'KT1', 'KT2', 'KT3',
    'HT1', 'HT2', 'HT3', 'HT4', 'HT5',
    'CS1', 'CS2', 'CS3', 'CS4', 'CS5',
    'JS1', 'JS2', 'JS3', 'JS4', 'JS5', 'JS6', 'JS7', 'JS8', 'JS9', 'JS10', 'JS11', 'JS12', 'JS13',
    'IN1', 'IN2', 'IN3', 'IN4', 'IN5',
    'DG1', 'DG2', 'DG3',
    'WS1', 'WS2', 'WS3', 'WS4', 'WS5',
    'CD1', 'CD2', 'CD3', 'CD4', 'CD5',
    'GT1', 'GT2', 'GT3',
    'GH1', 'GH2', 'GH3',
    'AP1', 'AP2', 'AP3',
    'RS1', 'RS2', 'RS3',
    'SP1', 'SP2', 'SP3',
    'AG1', 'AG2', 'AG3',
    'DV1', 'DV2', 'DV3',
    'OP1', 'OP2', 'OP3', 'OP4', 'OP5',
    'SD1', 'SD2', 'SD3',
    'CC1', 'CC2', 'CC3',
    'TD1', 'TD2', 'TD3',
    'CA1', 'CA2', 'CA3',
    'VC1', 'VC2', 'VC3',
    'II1', 'II2', 'II3',
    'AD1', 'AD2', 'AD3',
    'AN1', 'AN2', 'AN3',
    'ND1', 'ND2', 'ND3',
    'RE1', 'RE2', 'RE3',
    'SPG1', 'SPG2', 'SPG3',
    'DJ1', 'DJ2', 'DJ3',
    'BT1', 'BT2', 'BT3',
    'SQ1', 'SQ2', 'SQ3',
    'PS1', 'PS2', 'PS3',
    'DC1', 'DC2', 'DC3',
    'FB1', 'FB2', 'FB3'
);

ALTER TYPE techmaps_platform.task_body OWNER TO "techmaps";

DROP TABLE IF EXISTS techmaps_platform.task CASCADE;

CREATE TABLE techmaps_platform.task(
    id uuid NOT NULL,
    stage_id uuid NOT NULL,
    theme techmaps_platform.stage_theme,
    info techmaps_platform.task_body,
    task_number int,
    repository_link varchar,
    date_created timestamp,
    date_finished timestamp,
    dashboard_id uuid NOT NULL
);

ALTER TABLE techmaps_platform.task OWNER TO "techmaps";

ALTER TABLE techmaps_platform.task
    ADD CONSTRAINT task_pkey PRIMARY KEY (id);

ALTER TABLE techmaps_platform.task
    ADD CONSTRAINT task_stage_id_fkey FOREIGN KEY (stage_id)
        REFERENCES techmaps_platform.stage(id) ON DELETE CASCADE;

ALTER TABLE techmaps_platform.task
    ADD CONSTRAINT task_dashboard_id_fkey FOREIGN KEY (dashboard_id)
        REFERENCES techmaps_platform.dashboard(id) ON DELETE CASCADE;

DROP TABLE IF EXISTS techmaps_platform.task_commit;

CREATE TABLE techmaps_platform.task_commit(
    id uuid NOT NULL,
    task_id uuid NOT NULL,
    tag varchar NOT NULL,
    is_staged boolean NOT NULL,
    dashboard_id uuid NOT NULL
);

ALTER TABLE techmaps_platform.task_commit OWNER TO "techmaps";

ALTER TABLE techmaps_platform.task_commit
    ADD CONSTRAINT task_commit_pkey PRIMARY KEY (id);

ALTER TABLE techmaps_platform.task_commit
    ADD CONSTRAINT task_commit_task_id_fkey FOREIGN KEY (task_id)
        REFERENCES techmaps_platform.task(id) ON DELETE CASCADE;

ALTER TABLE techmaps_platform.task_commit
    ADD CONSTRAINT task_commit_dashboard_id_fkey FOREIGN KEY (dashboard_id)
        REFERENCES techmaps_platform.dashboard(id) ON DELETE CASCADE;

DROP TABLE IF EXISTS techmaps_platform.task_step CASCADE;

CREATE TABLE techmaps_platform.task_step (
    id uuid NOT NULL,
    task_id uuid NOT NULL,
    position integer NOT NULL,
    text varchar(255) NOT NULL,
    link varchar,
    is_finished boolean NOT NULL,
    is_priority boolean NOT NULL
);

ALTER TABLE techmaps_platform.task_step OWNER TO "techmaps";

ALTER TABLE techmaps_platform.task_step
    ADD CONSTRAINT task_step_pkey PRIMARY KEY (id);

ALTER TABLE techmaps_platform.task_step
    ADD CONSTRAINT task_step_task_id_fkey FOREIGN KEY (task_id)
        REFERENCES techmaps_platform.task(id) ON DELETE CASCADE;

DROP TABLE IF EXISTS techmaps_platform.task_step_description;

CREATE TABLE techmaps_platform.task_step_description (
    id uuid NOT NULL,
    info techmaps_platform.task_body,
    description varchar(255),
    link varchar,
    desc_number integer NOT NULL
);

ALTER TABLE techmaps_platform.task_step_description OWNER TO "techmaps";

ALTER TABLE techmaps_platform.task_step_description
    ADD CONSTRAINT task_step_description_pkey PRIMARY KEY (id);