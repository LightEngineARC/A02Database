package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MusicDatabase
{
	public static void main(String[] args) throws SQLException
	{
		// executeSqlStatement(SongsSql.fillTable());
		executeQueries(SongsSql.query_All(), ArtistsSql.query_All(), AlbumSql.query_All());

		System.out.println("done\n");
	}

	public static void executeSqlStatement(String... sqlStatements) throws SQLException
	{
		try (Connection connection = DriverManager.getConnection("jdbc:derby:MusicDatabase");
				// attribute "...;create=true" removed after database creation
				Statement statement = connection.createStatement();)
		{
			for (String sqlStatement : sqlStatements)
			{
				statement.execute(sqlStatement);
			}
		}

	}

	private static void printData(ResultSet s) throws SQLException
	{

		ResultSetMetaData metaData = s.getMetaData();
		int columnCount = metaData.getColumnCount();

		printHeader(metaData);
		while (s.next())
		{
			for (int i = 1; i <= columnCount; i++)
			{
				System.out.printf("%-" + metaData.getColumnLabel(i).length() + "s ", s.getObject(i) + " ");
			}

			System.out.println("");

		}
		System.out.println();

	}

	/**
	 * Method : printHeader
	 *
	 * Purpose : prints column headers and separated by two spaces and adds a line
	 * of dashes
	 *
	 * @param :
	 *            metadata
	 * @throws SQLException
	 *
	 ***************************************************
	 */
	private static void printHeader(ResultSetMetaData metaData) throws SQLException
	{
		int columnCount = metaData.getColumnCount();
		// print column headers
		for (int i = 1; i <= columnCount; i++)
		{
			System.out.print(metaData.getColumnLabel(i) + "  ");
		}
		System.out.println();
		// print dashed line
		for (int i = 1; i <= columnCount; i++)
		{
			for (int j = 0; j < metaData.getColumnLabel(i).length(); j++)
			{
				System.out.print("-");
			}
			if (i != columnCount)
			{
				System.out.print("--");
			}

		}
		System.out.println();
	}

	private static void executeQueries(String... query) throws SQLException
	{
		try (Connection connection = DriverManager.getConnection("jdbc:derby:MusicDatabase");
				Statement statement = connection.createStatement();)
		{
			for (String queries : query)
			{
				ResultSet results = statement.executeQuery(queries);
				printData(results);
			}

		}

	}
}
