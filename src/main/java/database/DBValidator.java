package database;

import com.mongodb.WriteResult;
import exceptions.DatabaseException;

/**
 *
 */
public class DBValidator
{
	/**
	 * Validates the result of a DB operation. Only works with Removal and Update - not inserting
	 * @param result the result to validate
	 */
	public static void validate(WriteResult result)
	{
		String error = result.getError();
		if(error != null)
		{
			throw new DatabaseException(error);
		}
		else if(result.getN() == 0)
		{
			throw new DatabaseException("Database operation did not complete");
		}
	}
}
