-- 1) Pre-create tag entries
INSERT INTO tag (id, name) VALUES
                               (1, 'web'),
                               (2, 'mobile'),
                               (3, 'email');

-- 2) Insert 1 000 translations in one statement
INSERT INTO translation (id, locale, translation_key, content)
SELECT
    x as id,
    'en' as locale,
    CONCAT('key_', x) as translation_key,
  CONCAT('Content for ', x) as content
FROM SYSTEM_RANGE(1, 1000);

-- 3) Associate each translation with two tags (web + mobile)
INSERT INTO translation_tags (translation_id, tag_id)
SELECT t.id, tg.id
FROM translation t
         -- pick tag id 1 (web)
         JOIN (SELECT 1 AS id UNION ALL SELECT 2 AS id) tg
WHERE t.locale = 'en';

-- If you want random tag assignment instead, you could do something like:
-- INSERT INTO translation_tags (translation_id, tag_id)
-- SELECT t.id, FLOOR(RAND()*3)+1
--   FROM translation t
--  WHERE t.locale = 'en';
