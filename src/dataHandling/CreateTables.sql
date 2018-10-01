CREATE TABLE artists (
  id   INTEGER      NOT NULL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE songs (
  id     INTEGER      NOT NULL PRIMARY KEY,
  name   VARCHAR(255) NOT NULL,
  length INTEGER CHECK (length IS NULL OR length > 0)
);

CREATE TABLE albums (
  id   INTEGER      NOT NULL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE album_versions (
  id    INTEGER      NOT NULL PRIMARY KEY,
  name  VARCHAR(255) NOT NULL,
  album INTEGER      NOT NULL,
  CONSTRAINT fk_album_version_album FOREIGN KEY (album) REFERENCES albums (id)
);

CREATE TABLE l_artists_songs (
  id     INTEGER NOT NULL PRIMARY KEY,
  artist INTEGER NOT NULL,
  song   INTEGER NOT NULL,
  CONSTRAINT fk_artist_songs FOREIGN KEY (artist) REFERENCES artists (id),
  CONSTRAINT fk_songs_artists FOREIGN KEY (song) REFERENCES songs (id)
);


CREATE TABLE l_artists_album_versions (
  id            INTEGER NOT NULL PRIMARY KEY,
  artist        INTEGER NOT NULL,
  album_version INTEGER NOT NULL,
  CONSTRAINT fk_artists_albums FOREIGN KEY (artist) REFERENCES artists (id),
  CONSTRAINT fk_albums_artists FOREIGN KEY (album_version) REFERENCES album_versions (id)
);

CREATE TABLE l_songs_album_versions (
  id            INTEGER NOT NULL PRIMARY KEY,
  song          INTEGER NOT NULL,
  album_version INTEGER NOT NULL,
  CONSTRAINT fk_songs_album_versions FOREIGN KEY (song) REFERENCES songs (id),
  CONSTRAINT fk_album_versions_songs FOREIGN KEY (album_version) REFERENCES album_versions (id)
);

