INSERT INTO Role (Name       , LoginToApp, DeleteThread, DeletePost, UpdateTimeToLive, CreateModerator)
    VALUES        ('Owner'    , 1         , 1           , 1         , 1               , 1              );
INSERT INTO Role (Name       , LoginToApp, DeleteThread, DeletePost, UpdateTimeToLive, CreateModerator)
    VALUES        ('Moderator', 1         , 1           , 1         , 0               , 0              );
COMMIT;