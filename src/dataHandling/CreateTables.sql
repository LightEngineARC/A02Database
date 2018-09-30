CREATE TABLE artists (
  id      INTEGER      NOT NULL PRIMARY KEY,
  name    VARCHAR(255) NOT NULL,
  comment VARCHAR(255) NOT NULL DEFAULT ''
);

CREATE TABLE l_artists_songs (
  id     INTEGER NOT NULL PRIMARY KEY,
  artist INTEGER NOT NULL, -- references artists.id
  song   INTEGER NOT NULL -- references songs.id
);

CREATE TABLE l_artists_albums (
  id     INTEGER NOT NULL PRIMARY KEY,
  artist INTEGER NOT NULL, -- references artists.id
  song   INTEGER NOT NULL -- references songs.id
);

CREATE TABLE l_songs_albums (
  id    INTEGER NOT NULL PRIMARY KEY,
  song  INTEGER NOT NULL, -- references recording.id
  album INTEGER NOT NULL -- references release.id
);

CREATE TABLE songs (
  id      INTEGER      NOT NULL PRIMARY KEY,
  name    VARCHAR(255) NOT NULL,
  length  INTEGER CHECK (length IS NULL OR length > 0),
  comment VARCHAR(255) NOT NULL DEFAULT ''
);

CREATE TABLE albums (
  id   INTEGER      NOT NULL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);
