-- Table: public.contents

-- DROP TABLE IF EXISTS public.contents;

CREATE EXTENSION vector;

CREATE TABLE IF NOT EXISTS public.contents
(
    embedding_id uuid NOT NULL,
    embedding vector(384),
    text text COLLATE pg_catalog."default",
    metadata json,
    CONSTRAINT contents_pkey PRIMARY KEY (embedding_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.contents
    OWNER to rag;

