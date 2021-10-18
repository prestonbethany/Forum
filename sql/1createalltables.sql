CREATE TABLE Role (
    ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    Name varchar(25) NOT NULL,
    LoginToApp BIT NOT NULL DEFAULT 0,
    DeleteThread BIT NOT NULL DEFAULT 0,
    DeletePost BIT NOT NULL DEFAULT 0,
    UpdateTimeToLive BIT NOT NULL DEFAULT 0,
    CreateModerator BIT NOT NULL DEFAULT 0,
    PRIMARY KEY (ID)
) ENGINE = InnoDB;
CREATE TABLE User (
    ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    RoleID INT UNSIGNED NOT NULL,
    Name varchar(50) NOT NULL,
    Password CHAR(64) NOT NULL,
    EmailAddress varchar(254) NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT `fk_user_role`
        FOREIGN KEY (RoleID) REFERENCES Role(ID)
) ENGINE = InnoDB;
CREATE TABLE Thread (
    ID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    Title varchar(255) NOT NULL,
    TimeToLive TINYINT UNSIGNED NOT NULL default 30,
    MaxTimeToLive TINYINT UNSIGNED  NOT NULL default 30,
    ArchivedFlag BIT NOT NULL DEFAULT 0,
    PRIMARY KEY (ID)
) ENGINE = InnoDB;
CREATE TABLE Post (
    ID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    ThreadID BIGINT UNSIGNED NOT NULL,
    Text varchar(2000) NOT NULL,
    ImagePath varchar(1024) DEFAULT '',
    DateAndTimeCreated DATETIME NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT `fk_post_thread`
        FOREIGN KEY (ThreadID) REFERENCES Thread(ID)
        ON DELETE CASCADE
        ON UPDATE RESTRICT
) ENGINE = InnoDB;
CREATE TABLE Audit (
    ID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    IPAddress VARBINARY(16) NOT NULL, #see for INET6_ATON() and INET6_NTOA(), which deals with both IP types: https://dev.mysql.com/doc/refman/5.7/en/miscellaneous-functions.html#function_inet6-aton
    PRIMARY KEY (ID)
) ENGINE = InnoDB;
COMMIT;