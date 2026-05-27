create table if not exists "app_user"
(
    id            varchar constraint app_user_pk primary key default gen_random_uuid()::varchar,
    first_name    varchar not null,
    last_name     varchar not null,
    email         varchar not null unique,
    password_hash varchar not null,
    role          varchar not null -- STUDENT, COMPANY, ADMIN
);

create table if not exists internship_track
(
    id          varchar constraint internship_track_pk primary key default gen_random_uuid()::varchar,
    title       varchar not null,
    description text
);

create table if not exists module
(
    id                  varchar constraint module_pk primary key default gen_random_uuid()::varchar,
    internship_track_id varchar not null constraint module_track_fk references internship_track (id),
    title               varchar not null,
    description         text,
    order_index         integer not null
);

create table if not exists task
(
    id              varchar constraint task_pk primary key default gen_random_uuid()::varchar,
    module_id       varchar not null constraint task_module_fk references module (id),
    title           varchar not null,
    description     text,
    container_track boolean not null default false,
    vm_track        boolean not null default false,
    order_index     integer not null
);

create table if not exists task_progress
(
    id              varchar constraint task_progress_pk primary key default gen_random_uuid()::varchar,
    task_id         varchar not null constraint progress_task_fk references task (id),
    user_id         varchar not null constraint progress_user_fk references "app_user" (id),
    status          varchar not null, -- STARTED, SUBMITTED, COMPLETED
    submission_data text,
    constraint task_progress_unique unique (task_id, user_id)
);
