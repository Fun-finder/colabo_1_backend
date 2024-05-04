-- article table ddl
CREATE TABLE colab_article (
	comment_id serial4 NOT NULL,
	title varchar(100) NOT NULL,
	user_id varchar(50) NOT NULL,
	emition_cd int4 NOT NULL DEFAULT 4,
	reason varchar(100) NULL,
	"content" text NULL,
	reg_dt timestamp NOT NULL DEFAULT now(),
	upd_dt timestamp NULL DEFAULT now()
);
COMMENT ON TABLE colab_article IS '일기 게시물입니다.';

-- Column comments

COMMENT ON COLUMN colab_article.comment_id IS 'article pk';
COMMENT ON COLUMN colab_article.title IS '일기 제목으로 작성하지 않을 경우 string 타입의 dateformat이 들어갑니다 (''YYYY-mm-dd'')';
COMMENT ON COLUMN colab_article.user_id IS '작성자의 user_id 입니다. (colab_user.user_id)';
COMMENT ON COLUMN colab_article.emition_cd IS '감정 코드입니다.(1:최악, 2:슬픔, 3:화남, 4:보통, 5: 좋음, 6: 즐거움, 7: 행복)';
COMMENT ON COLUMN colab_article.reason IS '감정의 이유입니다. 간단한 키워드를 받게 할 예정입니다. ';
COMMENT ON COLUMN colab_article."content" IS '일기의 내용입니다. html 에디터를 사용한다면 태그도 포함될 수 있습니다.';
COMMENT ON COLUMN colab_article.reg_dt IS '작성한 날짜입니다. (register date)';
COMMENT ON COLUMN colab_article.upd_dt IS '수정한 날짜입니다. (update_date)';
-- set pk
ALTER TABLE colab_article ADD CONSTRAINT colab_article_pk PRIMARY KEY (comment_id);



--
--
-- picture table ddl
CREATE TABLE colab_pictrue (
	pic_id serial4 NOT NULL,
	file_id int8 NOT NULL,
	thumbnail text NULL,
	reg_id varchar(100) NOT NULL,
	reg_dt timestamp NOT NULL DEFAULT now(),
	article_id int4 NOT NULL,
	CONSTRAINT colab_pictrue_pk PRIMARY KEY (pic_id)
);
COMMENT ON TABLE colab_pictrue IS '사진 파일이 저장되는 테이블입니다.';

-- Column comments

COMMENT ON COLUMN colab_pictrue.pic_id IS 'colab_picture pk';
COMMENT ON COLUMN colab_pictrue.file_id IS '파일id입니다. colab_file.file_id';
COMMENT ON COLUMN colab_pictrue.thumbnail IS 'base64기반의 압출 이미지 파일입니다.';
COMMENT ON COLUMN colab_pictrue.reg_id IS '파일을 올리는 사용자 id 입니다. (colab_user.user_id)';
COMMENT ON COLUMN colab_pictrue.reg_dt IS '작성일';
COMMENT ON COLUMN colab_pictrue.article_id IS '게시물 id 입니다. 매핑테이블 대신 사용합니다.(colab_article.article_id)';
ALTER TABLE colab_article ADD pic_cnt smallint NOT NULL DEFAULT 0;
COMMENT ON COLUMN colab_article.pic_cnt IS '사진의 개수입니다.';


--
--
-- file table ddl
CREATE TABLE colab_file (
	file_id int8 NOT NULL,
	"name" varchar(100) NOT NULL,
	loc varchar(200) NOT NULL,
	ext varchar(50) NOT NULL,
	"size" int4 NOT NULL,
	CONSTRAINT colab_file_pk PRIMARY KEY (file_id)
);
COMMENT ON TABLE colab_file IS '파일 테이블입니다.';

-- Column comments

COMMENT ON COLUMN colab_file.file_id IS 'colab_file pk';
COMMENT ON COLUMN colab_file."name" IS '파일이 원본명입니다.';
COMMENT ON COLUMN colab_file.loc IS '파일이 서버에 저장되는 주소입니다. (ex 2024/05/04/userid)';
COMMENT ON COLUMN colab_file.ext IS '파일의 확장자입니다.';
COMMENT ON COLUMN colab_file."size" IS '파일의 사이즈입니다. 단위는 byte';
