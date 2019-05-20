use blog;

create table user(
	id	int(11)	not null auto_increment primary key,
    username	varchar(255)	not null,
    password	varchar(255)	not null,
    phone	varchar(20)		not null,
    gender	varchar(30)	not null,
    real_name	varchar(255),
    birthday	varchar(50),
    email	varchar(255),
    personal_profile	varchar(255),
    recently_login	varchar(255)
) engine = InnoDB char set = utf8;

create table article(
	id	int(11)	not null auto_increment primary key,
    article_id	bigint(20) not null,
    author	varchar(255) not null,
    article_title	varchar(255) not null,
    article_content	longtext not null,
	article_tags	varchar(255) not null,
    article_type	varchar(255) not null,
    article_categories	varchar(255) not null,
    publish_date	varchar(255) not null,
    update_date		varchar(255) not null,
    article_url		varchar(255) not null,
    article_tabloid	text	not null,
    likes	int(11)	not null,
    last_article_id bigint(20),
    next_article_id bigint(20)
) engine = InnoDB char set = utf8;

create table comment_record(
	id	bigint(20)	not null auto_increment primary key,
    p_id	bigint(20)	not null,
    article_id bigint(20) not null,
	commenter_id	int(11)	not null,
    respondent_id	int(11) not null,
    comment_date varchar(100)	not null,
    likes	int(11)	not null,
    comment_content	text	not null,
    is_read int(1) not null
) engine = InnoDB char set = utf8;

drop table article;

create table archives(
	id	int(11)	not null	auto_increment primary key,
    archive_name	varchar(255)	not null
) engine = InnoDB char set = utf8;

create table article_likes(
	id bigint(20)	not null auto_increment primary key,
    article_id	bigint(20) not null,
    liker_id	int(11)	not null,
    like_date	varchar(255)	not null
) engine = InnoDB char set = utf8;

create table categories(
	id	int(11)	not null	auto_increment primary key,
    category_name	varchar(255)	not null
) engine = InnoDB char set = utf8;

create table comment_likes(
	id	int(11) not null	auto_increment primary key,
    article_id bigint(20)	not null,
    p_id int(11)	not null,
    liker_id	int(11)	not null,
    like_date	varchar(255)	not null
) engine = InnoDB char set = utf8;

create table daily_words(
	id int(11)	not null	auto_increment primary key,
    content	text not null,
    mood	varchar(30)	not null,
    publish_date	datetime	not null
) engine = InnoDB char set = utf8;

create table feedback(
	id	int(11)	not null	auto_increment primary key,
    feedback_content	text	not null,
    contact_info	varchar(255),
    person_id	int(11)	not null,
    feedback_date	varchar(255)	not null
) engine = InnoDB char set = utf8;

create table leave_message(
	id	int(11)	not null	auto_increment primary key,
    page_name	varchar(255)	not null,
    p_id	int(11)	not null,
    commenter_id	int(11)	not null,
    respondent_id	int(11)	not null,
    leave_message_date	varchar(255)	not null,
    likes	int(11)	not null,
	leave_messaage_content	text	not null,
    is_read	int(1)	not null
) engine = InnoDB char set = utf8;

create table leave_message_likes(
	id	int(11)	not null	auto_increment primary key,
    page_name	varchar(255)	not null,
    p_id	int(11)	not null,
    liker_id	int(11)	not null,
    like_date	varchar(255)	not null
) engine = InnoDB char set = utf8;

create table private_message(
	id	int(11)	not null	auto_increment primary key,
    private_message	varchar(255)	not null,
    publisher_id	int(11)	not null,
    replier_id	int(11)	not null,
    reply_content	varchar(255)	not null,
    publisher_date	varchar(255)	not null
) engine = InnoDB char set = utf8;

create table role(
	id	int(11)	not null	auto_increment primary key,
    name	varchar(255)	not null
) engine = InnoDB char set = utf8;

create table tags(
	id	int(11)	not null	auto_increment primary key,
    tag_name	varchar(255)	not null,
    tag_size	int(11)	not null
) engine = InnoDB char set = utf8;

create table user_role(
	user_id	int(11)	not null,
    role_id	int(11)	not null
) engine = InnoDB char set = utf8;

create table visitor(
	id	int(11)	not null	auto_increment primary key,
    visitor_count	bigint(20)	not null,
    page_name	text	not null
) engine = InnoDB char set = utf8;