using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab2
{
	internal class Commands
	{

		public static string GetSelectCommand(string tableName, string columns) =>
			$"SELECT {columns} FROM {tableName}";

		public static string GetInsertCommand(string tableName, string columns) =>
			$"INSERT INTO {tableName} ({columns}) VALUES "+
			$"({string.Join(",", columns.Split(',').Select(col => col.Trim()).ToList().Select(col => '@' + col))});";

		public static string GetDeleteCommand(string tableName, string primaryKey) =>
			$"DELETE FROM {tableName} WHERE {primaryKey} = @{primaryKey};";

		public static string GetUpdateCommand(string tableName, string columns, string primaryKey) =>
			$"UPDATE {tableName} SET " +
			$"{string.Join(',', columns.Split(',').Select(col => col.Trim()).ToList().Select(col => col + '=' + '@' + col))} " +
			$"WHERE {primaryKey}=@{primaryKey};";
	}
}
